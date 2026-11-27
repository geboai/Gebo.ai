package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCluster;
import ai.gebo.gitlab.client.model.APIEntitiesClusterGroup;
import ai.gebo.gitlab.client.model.APIEntitiesClusterProject;
import ai.gebo.gitlab.client.model.APIEntitiesDiscoveredClusters;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4AdminClustersAddRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdClustersUserRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdClustersUserRequest;
import ai.gebo.gitlab.client.model.PutApiV4AdminClustersClusterIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdClustersClusterIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdClustersClusterIdRequest;

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
public class ClustersApi {
    private ApiClient apiClient;

    public ClustersApi() {
        this(new ApiClient());
    }

    public ClustersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete instance cluster
     * Deletes an existing instance cluster. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param clusterId ID of the cluster.
     * @return APIEntitiesCluster
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4AdminClustersClusterIdRequestCreation(@jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        // verify the required parameter 'clusterId' is set
        if (clusterId == null) {
            throw new RestClientResponseException("Missing the required parameter 'clusterId' when calling deleteApiV4AdminClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("cluster_id", clusterId);

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

        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/clusters/{cluster_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete instance cluster
     * Deletes an existing instance cluster. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param clusterId ID of the cluster.
     * @return APIEntitiesCluster
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCluster deleteApiV4AdminClustersClusterId(@jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminClustersClusterIdRequestCreation(clusterId).body(localVarReturnType);
    }

    /**
     * Delete instance cluster
     * Deletes an existing instance cluster. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param clusterId ID of the cluster.
     * @return ResponseEntity&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCluster> deleteApiV4AdminClustersClusterIdWithHttpInfo(@jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminClustersClusterIdRequestCreation(clusterId).toEntity(localVarReturnType);
    }

    /**
     * Delete instance cluster
     * Deletes an existing instance cluster. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param clusterId ID of the cluster.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4AdminClustersClusterIdWithResponseSpec(@jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        return deleteApiV4AdminClustersClusterIdRequestCreation(clusterId);
    }

    /**
     * Delete a group cluster
     * Deletes a specified group cluster. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @return APIEntitiesClusterGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdClustersClusterIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'clusterId' is set
        if (clusterId == null) {
            throw new RestClientResponseException("Missing the required parameter 'clusterId' when calling deleteApiV4GroupsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("cluster_id", clusterId);

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

        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/clusters/{cluster_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group cluster
     * Deletes a specified group cluster. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @return APIEntitiesClusterGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClusterGroup deleteApiV4GroupsIdClustersClusterId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdClustersClusterIdRequestCreation(id, clusterId).body(localVarReturnType);
    }

    /**
     * Delete a group cluster
     * Deletes a specified group cluster. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @return ResponseEntity&lt;APIEntitiesClusterGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClusterGroup> deleteApiV4GroupsIdClustersClusterIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdClustersClusterIdRequestCreation(id, clusterId).toEntity(localVarReturnType);
    }

    /**
     * Delete a group cluster
     * Deletes a specified group cluster. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdClustersClusterIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        return deleteApiV4GroupsIdClustersClusterIdRequestCreation(id, clusterId);
    }

    /**
     * Delete cluster from a project
     * Deletes a specified cluster from a project. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @return APIEntitiesClusterProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdClustersClusterIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'clusterId' is set
        if (clusterId == null) {
            throw new RestClientResponseException("Missing the required parameter 'clusterId' when calling deleteApiV4ProjectsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("cluster_id", clusterId);

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

        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/clusters/{cluster_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete cluster from a project
     * Deletes a specified cluster from a project. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @return APIEntitiesClusterProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClusterProject deleteApiV4ProjectsIdClustersClusterId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdClustersClusterIdRequestCreation(id, clusterId).body(localVarReturnType);
    }

    /**
     * Delete cluster from a project
     * Deletes a specified cluster from a project. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @return ResponseEntity&lt;APIEntitiesClusterProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClusterProject> deleteApiV4ProjectsIdClustersClusterIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdClustersClusterIdRequestCreation(id, clusterId).toEntity(localVarReturnType);
    }

    /**
     * Delete cluster from a project
     * Deletes a specified cluster from a project. Does not remove existing resources in the connected Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdClustersClusterIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdClustersClusterIdRequestCreation(id, clusterId);
    }

    /**
     * List all instance clusters
     * Lists all instance clusters for the instance.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminClustersRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesCluster>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/clusters", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all instance clusters
     * Lists all instance clusters for the instance.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCluster> getApiV4AdminClusters() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCluster>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminClustersRequestCreation().body(localVarReturnType);
    }

    /**
     * List all instance clusters
     * Lists all instance clusters for the instance.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;List&lt;APIEntitiesCluster&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCluster>> getApiV4AdminClustersWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCluster>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminClustersRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all instance clusters
     * Lists all instance clusters for the instance.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminClustersWithResponseSpec() throws RestClientResponseException {
        return getApiV4AdminClustersRequestCreation();
    }

    /**
     * Retrieve a single instance cluster
     * Retrieves a specified instance cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param clusterId ID of the cluster.
     * @return APIEntitiesCluster
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminClustersClusterIdRequestCreation(@jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        // verify the required parameter 'clusterId' is set
        if (clusterId == null) {
            throw new RestClientResponseException("Missing the required parameter 'clusterId' when calling getApiV4AdminClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("cluster_id", clusterId);

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

        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/clusters/{cluster_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a single instance cluster
     * Retrieves a specified instance cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param clusterId ID of the cluster.
     * @return APIEntitiesCluster
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCluster getApiV4AdminClustersClusterId(@jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminClustersClusterIdRequestCreation(clusterId).body(localVarReturnType);
    }

    /**
     * Retrieve a single instance cluster
     * Retrieves a specified instance cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param clusterId ID of the cluster.
     * @return ResponseEntity&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCluster> getApiV4AdminClustersClusterIdWithHttpInfo(@jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminClustersClusterIdRequestCreation(clusterId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a single instance cluster
     * Retrieves a specified instance cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param clusterId ID of the cluster.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminClustersClusterIdWithResponseSpec(@jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        return getApiV4AdminClustersClusterIdRequestCreation(clusterId);
    }

    /**
     * List all certificate-based clusters
     * Lists all certificate-based clusters registered to a group, its subgroups, or its projects. Both enabled and disabled clusters are returned. Only clusters in groups and projects where the user has at least the Maintainer role are returned. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param groupId ID of the group to find all certificate-based clusters in the hierarchy.
     * @return APIEntitiesDiscoveredClusters
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4DiscoverCertBasedClustersRequestCreation(@jakarta.annotation.Nullable Integer groupId) throws RestClientResponseException {
        // verify the required parameter 'groupId' is set
        if (groupId == null) {
            throw new RestClientResponseException("Missing the required parameter 'groupId' when calling getApiV4DiscoverCertBasedClusters", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "group_id", groupId));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesDiscoveredClusters> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/discover-cert-based-clusters", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all certificate-based clusters
     * Lists all certificate-based clusters registered to a group, its subgroups, or its projects. Both enabled and disabled clusters are returned. Only clusters in groups and projects where the user has at least the Maintainer role are returned. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param groupId ID of the group to find all certificate-based clusters in the hierarchy.
     * @return APIEntitiesDiscoveredClusters
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDiscoveredClusters getApiV4DiscoverCertBasedClusters(@jakarta.annotation.Nullable Integer groupId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscoveredClusters> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DiscoverCertBasedClustersRequestCreation(groupId).body(localVarReturnType);
    }

    /**
     * List all certificate-based clusters
     * Lists all certificate-based clusters registered to a group, its subgroups, or its projects. Both enabled and disabled clusters are returned. Only clusters in groups and projects where the user has at least the Maintainer role are returned. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param groupId ID of the group to find all certificate-based clusters in the hierarchy.
     * @return ResponseEntity&lt;APIEntitiesDiscoveredClusters&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDiscoveredClusters> getApiV4DiscoverCertBasedClustersWithHttpInfo(@jakarta.annotation.Nullable Integer groupId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDiscoveredClusters> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DiscoverCertBasedClustersRequestCreation(groupId).toEntity(localVarReturnType);
    }

    /**
     * List all certificate-based clusters
     * Lists all certificate-based clusters registered to a group, its subgroups, or its projects. Both enabled and disabled clusters are returned. Only clusters in groups and projects where the user has at least the Maintainer role are returned. This feature was introduced in GitLab 17.9.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param groupId ID of the group to find all certificate-based clusters in the hierarchy.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4DiscoverCertBasedClustersWithResponseSpec(@jakarta.annotation.Nullable Integer groupId) throws RestClientResponseException {
        return getApiV4DiscoverCertBasedClustersRequestCreation(groupId);
    }

    /**
     * List all group clusters
     * Lists all group clusters for a specified group.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdClustersRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdClusters", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCluster>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/clusters", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group clusters
     * Lists all group clusters for a specified group.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCluster> getApiV4GroupsIdClusters(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCluster>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdClustersRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group clusters
     * Lists all group clusters for a specified group.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCluster&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCluster>> getApiV4GroupsIdClustersWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCluster>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdClustersRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group clusters
     * Lists all group clusters for a specified group.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdClustersWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdClustersRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a group cluster
     * Retrieves a specified group cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @return APIEntitiesClusterGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdClustersClusterIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'clusterId' is set
        if (clusterId == null) {
            throw new RestClientResponseException("Missing the required parameter 'clusterId' when calling getApiV4GroupsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("cluster_id", clusterId);

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

        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/clusters/{cluster_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group cluster
     * Retrieves a specified group cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @return APIEntitiesClusterGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClusterGroup getApiV4GroupsIdClustersClusterId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdClustersClusterIdRequestCreation(id, clusterId).body(localVarReturnType);
    }

    /**
     * Retrieve a group cluster
     * Retrieves a specified group cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @return ResponseEntity&lt;APIEntitiesClusterGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClusterGroup> getApiV4GroupsIdClustersClusterIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdClustersClusterIdRequestCreation(id, clusterId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group cluster
     * Retrieves a specified group cluster.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdClustersClusterIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        return getApiV4GroupsIdClustersClusterIdRequestCreation(id, clusterId);
    }

    /**
     * List all clusters in a project
     * Lists all clusters in a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdClustersRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdClusters", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesCluster>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/clusters", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all clusters in a project
     * Lists all clusters in a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesCluster> getApiV4ProjectsIdClusters(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCluster>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClustersRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all clusters in a project
     * Lists all clusters in a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesCluster&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesCluster>> getApiV4ProjectsIdClustersWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesCluster>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClustersRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all clusters in a project
     * Lists all clusters in a specified project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdClustersWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdClustersRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a cluster from a project
     * Retrieves a specified cluster in a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @return APIEntitiesClusterProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdClustersClusterIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'clusterId' is set
        if (clusterId == null) {
            throw new RestClientResponseException("Missing the required parameter 'clusterId' when calling getApiV4ProjectsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("cluster_id", clusterId);

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

        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/clusters/{cluster_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a cluster from a project
     * Retrieves a specified cluster in a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @return APIEntitiesClusterProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClusterProject getApiV4ProjectsIdClustersClusterId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClustersClusterIdRequestCreation(id, clusterId).body(localVarReturnType);
    }

    /**
     * Retrieve a cluster from a project
     * Retrieves a specified cluster in a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @return ResponseEntity&lt;APIEntitiesClusterProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClusterProject> getApiV4ProjectsIdClustersClusterIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClustersClusterIdRequestCreation(id, clusterId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a cluster from a project
     * Retrieves a specified cluster in a project.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdClustersClusterIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId) throws RestClientResponseException {
        return getApiV4ProjectsIdClustersClusterIdRequestCreation(id, clusterId);
    }

    /**
     * Create an instance cluster
     * Creates an instance cluster by adding an existing Kubernetes cluster.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AdminClustersAddRequest The postApiV4AdminClustersAddRequest parameter
     * @return APIEntitiesCluster
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4AdminClustersAddRequestCreation(@jakarta.annotation.Nonnull PostApiV4AdminClustersAddRequest postApiV4AdminClustersAddRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4AdminClustersAddRequest' is set
        if (postApiV4AdminClustersAddRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4AdminClustersAddRequest' when calling postApiV4AdminClustersAdd", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/clusters/add", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4AdminClustersAddRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an instance cluster
     * Creates an instance cluster by adding an existing Kubernetes cluster.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AdminClustersAddRequest The postApiV4AdminClustersAddRequest parameter
     * @return APIEntitiesCluster
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCluster postApiV4AdminClustersAdd(@jakarta.annotation.Nonnull PostApiV4AdminClustersAddRequest postApiV4AdminClustersAddRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AdminClustersAddRequestCreation(postApiV4AdminClustersAddRequest).body(localVarReturnType);
    }

    /**
     * Create an instance cluster
     * Creates an instance cluster by adding an existing Kubernetes cluster.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AdminClustersAddRequest The postApiV4AdminClustersAddRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCluster> postApiV4AdminClustersAddWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4AdminClustersAddRequest postApiV4AdminClustersAddRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4AdminClustersAddRequestCreation(postApiV4AdminClustersAddRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an instance cluster
     * Creates an instance cluster by adding an existing Kubernetes cluster.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4AdminClustersAddRequest The postApiV4AdminClustersAddRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4AdminClustersAddWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4AdminClustersAddRequest postApiV4AdminClustersAddRequest) throws RestClientResponseException {
        return postApiV4AdminClustersAddRequestCreation(postApiV4AdminClustersAddRequest);
    }

    /**
     * Create a group cluster
     * Creates a group cluster for a specified group by adding an existing Kubernetes cluster.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdClustersUserRequest The postApiV4GroupsIdClustersUserRequest parameter
     * @return APIEntitiesClusterGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdClustersUserRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdClustersUserRequest postApiV4GroupsIdClustersUserRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdClustersUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdClustersUserRequest' is set
        if (postApiV4GroupsIdClustersUserRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdClustersUserRequest' when calling postApiV4GroupsIdClustersUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/clusters/user", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdClustersUserRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group cluster
     * Creates a group cluster for a specified group by adding an existing Kubernetes cluster.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdClustersUserRequest The postApiV4GroupsIdClustersUserRequest parameter
     * @return APIEntitiesClusterGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClusterGroup postApiV4GroupsIdClustersUser(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdClustersUserRequest postApiV4GroupsIdClustersUserRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdClustersUserRequestCreation(id, postApiV4GroupsIdClustersUserRequest).body(localVarReturnType);
    }

    /**
     * Create a group cluster
     * Creates a group cluster for a specified group by adding an existing Kubernetes cluster.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdClustersUserRequest The postApiV4GroupsIdClustersUserRequest parameter
     * @return ResponseEntity&lt;APIEntitiesClusterGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClusterGroup> postApiV4GroupsIdClustersUserWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdClustersUserRequest postApiV4GroupsIdClustersUserRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdClustersUserRequestCreation(id, postApiV4GroupsIdClustersUserRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group cluster
     * Creates a group cluster for a specified group by adding an existing Kubernetes cluster.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdClustersUserRequest The postApiV4GroupsIdClustersUserRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdClustersUserWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdClustersUserRequest postApiV4GroupsIdClustersUserRequest) throws RestClientResponseException {
        return postApiV4GroupsIdClustersUserRequestCreation(id, postApiV4GroupsIdClustersUserRequest);
    }

    /**
     * Add a cluster to a project
     * Adds an existing Kubernetes cluster to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdClustersUserRequest The postApiV4ProjectsIdClustersUserRequest parameter
     * @return APIEntitiesClusterProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdClustersUserRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClustersUserRequest postApiV4ProjectsIdClustersUserRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdClustersUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdClustersUserRequest' is set
        if (postApiV4ProjectsIdClustersUserRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdClustersUserRequest' when calling postApiV4ProjectsIdClustersUser", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/clusters/user", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdClustersUserRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a cluster to a project
     * Adds an existing Kubernetes cluster to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdClustersUserRequest The postApiV4ProjectsIdClustersUserRequest parameter
     * @return APIEntitiesClusterProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClusterProject postApiV4ProjectsIdClustersUser(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClustersUserRequest postApiV4ProjectsIdClustersUserRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdClustersUserRequestCreation(id, postApiV4ProjectsIdClustersUserRequest).body(localVarReturnType);
    }

    /**
     * Add a cluster to a project
     * Adds an existing Kubernetes cluster to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdClustersUserRequest The postApiV4ProjectsIdClustersUserRequest parameter
     * @return ResponseEntity&lt;APIEntitiesClusterProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClusterProject> postApiV4ProjectsIdClustersUserWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClustersUserRequest postApiV4ProjectsIdClustersUserRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdClustersUserRequestCreation(id, postApiV4ProjectsIdClustersUserRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a cluster to a project
     * Adds an existing Kubernetes cluster to a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdClustersUserRequest The postApiV4ProjectsIdClustersUserRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdClustersUserWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClustersUserRequest postApiV4ProjectsIdClustersUserRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdClustersUserRequestCreation(id, postApiV4ProjectsIdClustersUserRequest);
    }

    /**
     * Update an instance cluster
     * Updates an existing instance cluster. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param clusterId ID of the cluster.
     * @param putApiV4AdminClustersClusterIdRequest The putApiV4AdminClustersClusterIdRequest parameter
     * @return APIEntitiesCluster
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminClustersClusterIdRequestCreation(@jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4AdminClustersClusterIdRequest putApiV4AdminClustersClusterIdRequest) throws RestClientResponseException {
        // verify the required parameter 'clusterId' is set
        if (clusterId == null) {
            throw new RestClientResponseException("Missing the required parameter 'clusterId' when calling putApiV4AdminClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("cluster_id", clusterId);

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

        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/clusters/{cluster_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminClustersClusterIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an instance cluster
     * Updates an existing instance cluster. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param clusterId ID of the cluster.
     * @param putApiV4AdminClustersClusterIdRequest The putApiV4AdminClustersClusterIdRequest parameter
     * @return APIEntitiesCluster
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCluster putApiV4AdminClustersClusterId(@jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4AdminClustersClusterIdRequest putApiV4AdminClustersClusterIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminClustersClusterIdRequestCreation(clusterId, putApiV4AdminClustersClusterIdRequest).body(localVarReturnType);
    }

    /**
     * Update an instance cluster
     * Updates an existing instance cluster. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param clusterId ID of the cluster.
     * @param putApiV4AdminClustersClusterIdRequest The putApiV4AdminClustersClusterIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCluster&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCluster> putApiV4AdminClustersClusterIdWithHttpInfo(@jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4AdminClustersClusterIdRequest putApiV4AdminClustersClusterIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCluster> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminClustersClusterIdRequestCreation(clusterId, putApiV4AdminClustersClusterIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an instance cluster
     * Updates an existing instance cluster. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param clusterId ID of the cluster.
     * @param putApiV4AdminClustersClusterIdRequest The putApiV4AdminClustersClusterIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminClustersClusterIdWithResponseSpec(@jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4AdminClustersClusterIdRequest putApiV4AdminClustersClusterIdRequest) throws RestClientResponseException {
        return putApiV4AdminClustersClusterIdRequestCreation(clusterId, putApiV4AdminClustersClusterIdRequest);
    }

    /**
     * Update a group cluster
     * Updates a specified group cluster. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @param putApiV4GroupsIdClustersClusterIdRequest The putApiV4GroupsIdClustersClusterIdRequest parameter
     * @return APIEntitiesClusterGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdClustersClusterIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4GroupsIdClustersClusterIdRequest putApiV4GroupsIdClustersClusterIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'clusterId' is set
        if (clusterId == null) {
            throw new RestClientResponseException("Missing the required parameter 'clusterId' when calling putApiV4GroupsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("cluster_id", clusterId);

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

        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/clusters/{cluster_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdClustersClusterIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group cluster
     * Updates a specified group cluster. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @param putApiV4GroupsIdClustersClusterIdRequest The putApiV4GroupsIdClustersClusterIdRequest parameter
     * @return APIEntitiesClusterGroup
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClusterGroup putApiV4GroupsIdClustersClusterId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4GroupsIdClustersClusterIdRequest putApiV4GroupsIdClustersClusterIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdClustersClusterIdRequestCreation(id, clusterId, putApiV4GroupsIdClustersClusterIdRequest).body(localVarReturnType);
    }

    /**
     * Update a group cluster
     * Updates a specified group cluster. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @param putApiV4GroupsIdClustersClusterIdRequest The putApiV4GroupsIdClustersClusterIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesClusterGroup&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClusterGroup> putApiV4GroupsIdClustersClusterIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4GroupsIdClustersClusterIdRequest putApiV4GroupsIdClustersClusterIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterGroup> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdClustersClusterIdRequestCreation(id, clusterId, putApiV4GroupsIdClustersClusterIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group cluster
     * Updates a specified group cluster. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param clusterId ID of the cluster.
     * @param putApiV4GroupsIdClustersClusterIdRequest The putApiV4GroupsIdClustersClusterIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdClustersClusterIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4GroupsIdClustersClusterIdRequest putApiV4GroupsIdClustersClusterIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdClustersClusterIdRequestCreation(id, clusterId, putApiV4GroupsIdClustersClusterIdRequest);
    }

    /**
     * Update a cluster in a project
     * Updates a cluster in a specified project. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @param putApiV4ProjectsIdClustersClusterIdRequest The putApiV4ProjectsIdClustersClusterIdRequest parameter
     * @return APIEntitiesClusterProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdClustersClusterIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4ProjectsIdClustersClusterIdRequest putApiV4ProjectsIdClustersClusterIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'clusterId' is set
        if (clusterId == null) {
            throw new RestClientResponseException("Missing the required parameter 'clusterId' when calling putApiV4ProjectsIdClustersClusterId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("cluster_id", clusterId);

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

        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/clusters/{cluster_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdClustersClusterIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a cluster in a project
     * Updates a cluster in a specified project. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @param putApiV4ProjectsIdClustersClusterIdRequest The putApiV4ProjectsIdClustersClusterIdRequest parameter
     * @return APIEntitiesClusterProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClusterProject putApiV4ProjectsIdClustersClusterId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4ProjectsIdClustersClusterIdRequest putApiV4ProjectsIdClustersClusterIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdClustersClusterIdRequestCreation(id, clusterId, putApiV4ProjectsIdClustersClusterIdRequest).body(localVarReturnType);
    }

    /**
     * Update a cluster in a project
     * Updates a cluster in a specified project. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @param putApiV4ProjectsIdClustersClusterIdRequest The putApiV4ProjectsIdClustersClusterIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesClusterProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClusterProject> putApiV4ProjectsIdClustersClusterIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4ProjectsIdClustersClusterIdRequest putApiV4ProjectsIdClustersClusterIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClusterProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdClustersClusterIdRequestCreation(id, clusterId, putApiV4ProjectsIdClustersClusterIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a cluster in a project
     * Updates a cluster in a specified project. The &#x60;name&#x60;, &#x60;api_url&#x60;, &#x60;ca_cert&#x60;, and &#x60;token&#x60; attributes can be updated only if the cluster was added as an existing Kubernetes cluster.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param clusterId ID of the cluster.
     * @param putApiV4ProjectsIdClustersClusterIdRequest The putApiV4ProjectsIdClustersClusterIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdClustersClusterIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer clusterId, @jakarta.annotation.Nullable PutApiV4ProjectsIdClustersClusterIdRequest putApiV4ProjectsIdClustersClusterIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdClustersClusterIdRequestCreation(id, clusterId, putApiV4ProjectsIdClustersClusterIdRequest);
    }
}
