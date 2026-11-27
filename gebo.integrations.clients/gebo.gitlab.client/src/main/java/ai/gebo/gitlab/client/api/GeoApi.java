package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesGeoNode;
import ai.gebo.gitlab.client.model.APIEntitiesGeoNodeStatus;
import ai.gebo.gitlab.client.model.APIEntitiesGeoPipelineRefs;
import ai.gebo.gitlab.client.model.APIEntitiesGeoSite;
import ai.gebo.gitlab.client.model.APIEntitiesGeoSiteStatus;
import ai.gebo.gitlab.client.model.PostApiV4GeoFailuresRequest;
import ai.gebo.gitlab.client.model.PostApiV4GeoNodesRequest;
import ai.gebo.gitlab.client.model.PostApiV4GeoProxyGitSshInfoRefsReceivePackRequest;
import ai.gebo.gitlab.client.model.PostApiV4GeoProxyGitSshInfoRefsUploadPackRequest;
import ai.gebo.gitlab.client.model.PostApiV4GeoProxyGitSshReceivePackRequest;
import ai.gebo.gitlab.client.model.PostApiV4GeoProxyGitSshUploadPackRequest;
import ai.gebo.gitlab.client.model.PostApiV4GeoSitesRequest;
import ai.gebo.gitlab.client.model.PostApiV4GeoStatusRequest;
import ai.gebo.gitlab.client.model.PutApiV4GeoNodesIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4GeoSitesIdRequest;

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
public class GeoApi {
    private ApiClient apiClient;

    public GeoApi() {
        this(new ApiClient());
    }

    public GeoApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a Geo node
     * Deletes a specified Geo node.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GeoNodesIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GeoNodesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/geo_nodes/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a Geo node
     * Deletes a specified Geo node.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GeoNodesId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GeoNodesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a Geo node
     * Deletes a specified Geo node.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GeoNodesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GeoNodesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a Geo node
     * Deletes a specified Geo node.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GeoNodesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4GeoNodesIdRequestCreation(id);
    }

    /**
     * Delete a Geo site
     * Deletes a specified Geo site.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GeoSitesIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GeoSitesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/geo_sites/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a Geo site
     * Deletes a specified Geo site.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GeoSitesId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GeoSitesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a Geo site
     * Deletes a specified Geo site.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GeoSitesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GeoSitesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a Geo site
     * Deletes a specified Geo site.
     * <p><b>204</b> - 204 No Content
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GeoSitesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4GeoSitesIdRequestCreation(id);
    }

    /**
     * List all Geo nodes
     * Lists all Geo nodes for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGeoNode&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoNodesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesGeoNode>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_nodes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Geo nodes
     * Lists all Geo nodes for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGeoNode&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGeoNode> getApiV4GeoNodes(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGeoNode>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoNodesRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all Geo nodes
     * Lists all Geo nodes for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGeoNode&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGeoNode>> getApiV4GeoNodesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGeoNode>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoNodesRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all Geo nodes
     * Lists all Geo nodes for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoNodesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GeoNodesRequestCreation(page, perPage);
    }

    /**
     * Retrieve a single Geo node
     * Retrieves a single Geo Node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @return APIEntitiesGeoNode
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoNodesIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GeoNodesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGeoNode> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_nodes/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a single Geo node
     * Retrieves a single Geo Node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @return APIEntitiesGeoNode
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoNode getApiV4GeoNodesId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNode> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoNodesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a single Geo node
     * Retrieves a single Geo Node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @return ResponseEntity&lt;APIEntitiesGeoNode&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoNode> getApiV4GeoNodesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNode> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoNodesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a single Geo node
     * Retrieves a single Geo Node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoNodesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4GeoNodesIdRequestCreation(id);
    }

    /**
     * Retrieve metrics for a Geo node
     * Retrieves metrics for a specified Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @param refresh If &#x60;true&#x60;, fetches the latest status from the Geo node directly, ignoring the cache.
     * @return APIEntitiesGeoNodeStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoNodesIdStatusRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean refresh) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GeoNodesIdStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "refresh", refresh));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGeoNodeStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_nodes/{id}/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve metrics for a Geo node
     * Retrieves metrics for a specified Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @param refresh If &#x60;true&#x60;, fetches the latest status from the Geo node directly, ignoring the cache.
     * @return APIEntitiesGeoNodeStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoNodeStatus getApiV4GeoNodesIdStatus(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean refresh) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNodeStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoNodesIdStatusRequestCreation(id, refresh).body(localVarReturnType);
    }

    /**
     * Retrieve metrics for a Geo node
     * Retrieves metrics for a specified Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @param refresh If &#x60;true&#x60;, fetches the latest status from the Geo node directly, ignoring the cache.
     * @return ResponseEntity&lt;APIEntitiesGeoNodeStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoNodeStatus> getApiV4GeoNodesIdStatusWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean refresh) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNodeStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoNodesIdStatusRequestCreation(id, refresh).toEntity(localVarReturnType);
    }

    /**
     * Retrieve metrics for a Geo node
     * Retrieves metrics for a specified Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @param refresh If &#x60;true&#x60;, fetches the latest status from the Geo node directly, ignoring the cache.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoNodesIdStatusWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean refresh) throws RestClientResponseException {
        return getApiV4GeoNodesIdStatusRequestCreation(id, refresh);
    }

    /**
     * List all Geo node statuses
     * Lists all Geo node statuses.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGeoNodeStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoNodesStatusRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesGeoNodeStatus>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_nodes/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Geo node statuses
     * Lists all Geo node statuses.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGeoNodeStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGeoNodeStatus> getApiV4GeoNodesStatus(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGeoNodeStatus>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoNodesStatusRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all Geo node statuses
     * Lists all Geo node statuses.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGeoNodeStatus&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGeoNodeStatus>> getApiV4GeoNodesStatusWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGeoNodeStatus>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoNodesStatusRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all Geo node statuses
     * Lists all Geo node statuses.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoNodesStatusWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GeoNodesStatusRequestCreation(page, perPage);
    }

    /**
     * Returns a Geo proxy response
     * Determines if the current Geo site should proxy requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoProxyRequestCreation() throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/geo/proxy", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Returns a Geo proxy response
     * Determines if the current Geo site should proxy requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GeoProxy() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GeoProxyRequestCreation().body(localVarReturnType);
    }

    /**
     * Returns a Geo proxy response
     * Determines if the current Geo site should proxy requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GeoProxyWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoProxyRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Returns a Geo proxy response
     * Determines if the current Geo site should proxy requests.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoProxyWithResponseSpec() throws RestClientResponseException {
        return getApiV4GeoProxyRequestCreation();
    }

    /**
     * List all pipeline refs for a project
     * Lists all pipeline refs for a project. Secondary runners use them to check the site is up to date.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param glRepository Repository to check.
     * @return APIEntitiesGeoPipelineRefs
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoRepositoriesGlRepositoryPipelineRefsRequestCreation(@jakarta.annotation.Nonnull String glRepository) throws RestClientResponseException {
        // verify the required parameter 'glRepository' is set
        if (glRepository == null) {
            throw new RestClientResponseException("Missing the required parameter 'glRepository' when calling getApiV4GeoRepositoriesGlRepositoryPipelineRefs", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("gl_repository", glRepository);

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

        ParameterizedTypeReference<APIEntitiesGeoPipelineRefs> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo/repositories/{gl_repository}/pipeline_refs", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all pipeline refs for a project
     * Lists all pipeline refs for a project. Secondary runners use them to check the site is up to date.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param glRepository Repository to check.
     * @return APIEntitiesGeoPipelineRefs
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoPipelineRefs getApiV4GeoRepositoriesGlRepositoryPipelineRefs(@jakarta.annotation.Nonnull String glRepository) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoPipelineRefs> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoRepositoriesGlRepositoryPipelineRefsRequestCreation(glRepository).body(localVarReturnType);
    }

    /**
     * List all pipeline refs for a project
     * Lists all pipeline refs for a project. Secondary runners use them to check the site is up to date.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param glRepository Repository to check.
     * @return ResponseEntity&lt;APIEntitiesGeoPipelineRefs&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoPipelineRefs> getApiV4GeoRepositoriesGlRepositoryPipelineRefsWithHttpInfo(@jakarta.annotation.Nonnull String glRepository) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoPipelineRefs> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoRepositoriesGlRepositoryPipelineRefsRequestCreation(glRepository).toEntity(localVarReturnType);
    }

    /**
     * List all pipeline refs for a project
     * Lists all pipeline refs for a project. Secondary runners use them to check the site is up to date.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param glRepository Repository to check.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoRepositoriesGlRepositoryPipelineRefsWithResponseSpec(@jakarta.annotation.Nonnull String glRepository) throws RestClientResponseException {
        return getApiV4GeoRepositoriesGlRepositoryPipelineRefsRequestCreation(glRepository);
    }

    /**
     * Retrieve a replicable file
     * Retrieves a replicable file for a Geo secondary site. Serves the file through a CDN or sendfile.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param replicableName Name of the replicable type, for example &#x60;package_file&#x60;.
     * @param replicableId ID of the replicable.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoRetrieveReplicableNameReplicableIdRequestCreation(@jakarta.annotation.Nonnull String replicableName, @jakarta.annotation.Nonnull Integer replicableId) throws RestClientResponseException {
        // verify the required parameter 'replicableName' is set
        if (replicableName == null) {
            throw new RestClientResponseException("Missing the required parameter 'replicableName' when calling getApiV4GeoRetrieveReplicableNameReplicableId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'replicableId' is set
        if (replicableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'replicableId' when calling getApiV4GeoRetrieveReplicableNameReplicableId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("replicable_name", replicableName);
        pathParams.put("replicable_id", replicableId);

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
        return apiClient.invokeAPI("/api/v4/geo/retrieve/{replicable_name}/{replicable_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a replicable file
     * Retrieves a replicable file for a Geo secondary site. Serves the file through a CDN or sendfile.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param replicableName Name of the replicable type, for example &#x60;package_file&#x60;.
     * @param replicableId ID of the replicable.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GeoRetrieveReplicableNameReplicableId(@jakarta.annotation.Nonnull String replicableName, @jakarta.annotation.Nonnull Integer replicableId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GeoRetrieveReplicableNameReplicableIdRequestCreation(replicableName, replicableId).body(localVarReturnType);
    }

    /**
     * Retrieve a replicable file
     * Retrieves a replicable file for a Geo secondary site. Serves the file through a CDN or sendfile.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param replicableName Name of the replicable type, for example &#x60;package_file&#x60;.
     * @param replicableId ID of the replicable.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GeoRetrieveReplicableNameReplicableIdWithHttpInfo(@jakarta.annotation.Nonnull String replicableName, @jakarta.annotation.Nonnull Integer replicableId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoRetrieveReplicableNameReplicableIdRequestCreation(replicableName, replicableId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a replicable file
     * Retrieves a replicable file for a Geo secondary site. Serves the file through a CDN or sendfile.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - Bad Request
     * @param replicableName Name of the replicable type, for example &#x60;package_file&#x60;.
     * @param replicableId ID of the replicable.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoRetrieveReplicableNameReplicableIdWithResponseSpec(@jakarta.annotation.Nonnull String replicableName, @jakarta.annotation.Nonnull Integer replicableId) throws RestClientResponseException {
        return getApiV4GeoRetrieveReplicableNameReplicableIdRequestCreation(replicableName, replicableId);
    }

    /**
     * List all Geo sites
     * Lists all Geo sites for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGeoSite&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoSitesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesGeoSite>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_sites", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Geo sites
     * Lists all Geo sites for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGeoSite&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGeoSite> getApiV4GeoSites(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGeoSite>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoSitesRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all Geo sites
     * Lists all Geo sites for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGeoSite&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGeoSite>> getApiV4GeoSitesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGeoSite>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoSitesRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all Geo sites
     * Lists all Geo sites for the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoSitesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GeoSitesRequestCreation(page, perPage);
    }

    /**
     * Retrieve a Geo site
     * Retrieves a specified Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @return APIEntitiesGeoSite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoSitesIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GeoSitesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGeoSite> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_sites/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Geo site
     * Retrieves a specified Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @return APIEntitiesGeoSite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoSite getApiV4GeoSitesId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSite> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoSitesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve a Geo site
     * Retrieves a specified Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @return ResponseEntity&lt;APIEntitiesGeoSite&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoSite> getApiV4GeoSitesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSite> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoSitesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Geo site
     * Retrieves a specified Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoSitesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4GeoSitesIdRequestCreation(id);
    }

    /**
     * Retrieve a Geo site status
     * Retrieves the status for a specified Geo site. When the legacy &#x60;geo_upload_replication&#x60; and &#x60;geo_upload_force_primary_checksumming&#x60; feature flags are disabled, the &#x60;uploads_*&#x60; fields are the sum of the values for each upload type. The &#x60;health_status&#x60; field is either &#x60;Healthy&#x60; or &#x60;Unhealthy&#x60;. The &#x60;health&#x60; field can be empty, &#x60;Healthy&#x60;, or contain the actual error message.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @param refresh If &#x60;true&#x60;, fetches the latest status from the Geo site directly, ignoring the cache.
     * @return APIEntitiesGeoSiteStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoSitesIdStatusRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean refresh) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GeoSitesIdStatus", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "refresh", refresh));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesGeoSiteStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_sites/{id}/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Geo site status
     * Retrieves the status for a specified Geo site. When the legacy &#x60;geo_upload_replication&#x60; and &#x60;geo_upload_force_primary_checksumming&#x60; feature flags are disabled, the &#x60;uploads_*&#x60; fields are the sum of the values for each upload type. The &#x60;health_status&#x60; field is either &#x60;Healthy&#x60; or &#x60;Unhealthy&#x60;. The &#x60;health&#x60; field can be empty, &#x60;Healthy&#x60;, or contain the actual error message.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @param refresh If &#x60;true&#x60;, fetches the latest status from the Geo site directly, ignoring the cache.
     * @return APIEntitiesGeoSiteStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoSiteStatus getApiV4GeoSitesIdStatus(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean refresh) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSiteStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoSitesIdStatusRequestCreation(id, refresh).body(localVarReturnType);
    }

    /**
     * Retrieve a Geo site status
     * Retrieves the status for a specified Geo site. When the legacy &#x60;geo_upload_replication&#x60; and &#x60;geo_upload_force_primary_checksumming&#x60; feature flags are disabled, the &#x60;uploads_*&#x60; fields are the sum of the values for each upload type. The &#x60;health_status&#x60; field is either &#x60;Healthy&#x60; or &#x60;Unhealthy&#x60;. The &#x60;health&#x60; field can be empty, &#x60;Healthy&#x60;, or contain the actual error message.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @param refresh If &#x60;true&#x60;, fetches the latest status from the Geo site directly, ignoring the cache.
     * @return ResponseEntity&lt;APIEntitiesGeoSiteStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoSiteStatus> getApiV4GeoSitesIdStatusWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean refresh) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSiteStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoSitesIdStatusRequestCreation(id, refresh).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Geo site status
     * Retrieves the status for a specified Geo site. When the legacy &#x60;geo_upload_replication&#x60; and &#x60;geo_upload_force_primary_checksumming&#x60; feature flags are disabled, the &#x60;uploads_*&#x60; fields are the sum of the values for each upload type. The &#x60;health_status&#x60; field is either &#x60;Healthy&#x60; or &#x60;Unhealthy&#x60;. The &#x60;health&#x60; field can be empty, &#x60;Healthy&#x60;, or contain the actual error message.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @param refresh If &#x60;true&#x60;, fetches the latest status from the Geo site directly, ignoring the cache.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoSitesIdStatusWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean refresh) throws RestClientResponseException {
        return getApiV4GeoSitesIdStatusRequestCreation(id, refresh);
    }

    /**
     * List all Geo site statuses
     * Lists all Geo site statuses. When the legacy &#x60;geo_upload_replication&#x60; and &#x60;geo_upload_force_primary_checksumming&#x60; feature flags are disabled, the &#x60;uploads_*&#x60; fields are the sum of the values for each upload type.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGeoSiteStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GeoSitesStatusRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesGeoSiteStatus>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_sites/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Geo site statuses
     * Lists all Geo site statuses. When the legacy &#x60;geo_upload_replication&#x60; and &#x60;geo_upload_force_primary_checksumming&#x60; feature flags are disabled, the &#x60;uploads_*&#x60; fields are the sum of the values for each upload type.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesGeoSiteStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesGeoSiteStatus> getApiV4GeoSitesStatus(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGeoSiteStatus>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoSitesStatusRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all Geo site statuses
     * Lists all Geo site statuses. When the legacy &#x60;geo_upload_replication&#x60; and &#x60;geo_upload_force_primary_checksumming&#x60; feature flags are disabled, the &#x60;uploads_*&#x60; fields are the sum of the values for each upload type.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesGeoSiteStatus&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesGeoSiteStatus>> getApiV4GeoSitesStatusWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesGeoSiteStatus>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GeoSitesStatusRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all Geo site statuses
     * Lists all Geo site statuses. When the legacy &#x60;geo_upload_replication&#x60; and &#x60;geo_upload_force_primary_checksumming&#x60; feature flags are disabled, the &#x60;uploads_*&#x60; fields are the sum of the values for each upload type.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GeoSitesStatusWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GeoSitesStatusRequestCreation(page, perPage);
    }

    /**
     * Create a report of persistent verification failures
     * Creates a report on the primary site of resources that persistently fail verification on a secondary site.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - 400 Bad request
     * @param postApiV4GeoFailuresRequest The postApiV4GeoFailuresRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoFailuresRequestCreation(@jakarta.annotation.Nonnull PostApiV4GeoFailuresRequest postApiV4GeoFailuresRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4GeoFailuresRequest' is set
        if (postApiV4GeoFailuresRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GeoFailuresRequest' when calling postApiV4GeoFailures", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/geo/failures", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GeoFailuresRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a report of persistent verification failures
     * Creates a report on the primary site of resources that persistently fail verification on a secondary site.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - 400 Bad request
     * @param postApiV4GeoFailuresRequest The postApiV4GeoFailuresRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GeoFailures(@jakarta.annotation.Nonnull PostApiV4GeoFailuresRequest postApiV4GeoFailuresRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GeoFailuresRequestCreation(postApiV4GeoFailuresRequest).body(localVarReturnType);
    }

    /**
     * Create a report of persistent verification failures
     * Creates a report on the primary site of resources that persistently fail verification on a secondary site.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - 400 Bad request
     * @param postApiV4GeoFailuresRequest The postApiV4GeoFailuresRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GeoFailuresWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4GeoFailuresRequest postApiV4GeoFailuresRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoFailuresRequestCreation(postApiV4GeoFailuresRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a report of persistent verification failures
     * Creates a report on the primary site of resources that persistently fail verification on a secondary site.
     * <p><b>202</b> - Accepted
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>404</b> - 404 Not found
     * <p><b>400</b> - 400 Bad request
     * @param postApiV4GeoFailuresRequest The postApiV4GeoFailuresRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoFailuresWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4GeoFailuresRequest postApiV4GeoFailuresRequest) throws RestClientResponseException {
        return postApiV4GeoFailuresRequestCreation(postApiV4GeoFailuresRequest);
    }

    /**
     * Query the GraphQL endpoint of an existing Geo site
     * Queries the GraphQL endpoint of an existing Geo site.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 GeoNode Not Found
     * <p><b>500</b> - 500 Internal Server Error
     * <p><b>400</b> - Bad Request
     * @param id The ID of the Geo node
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoNodeProxyIdGraphqlRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GeoNodeProxyIdGraphql", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/geo/node_proxy/{id}/graphql", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Query the GraphQL endpoint of an existing Geo site
     * Queries the GraphQL endpoint of an existing Geo site.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 GeoNode Not Found
     * <p><b>500</b> - 500 Internal Server Error
     * <p><b>400</b> - Bad Request
     * @param id The ID of the Geo node
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GeoNodeProxyIdGraphql(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GeoNodeProxyIdGraphqlRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Query the GraphQL endpoint of an existing Geo site
     * Queries the GraphQL endpoint of an existing Geo site.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 GeoNode Not Found
     * <p><b>500</b> - 500 Internal Server Error
     * <p><b>400</b> - Bad Request
     * @param id The ID of the Geo node
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GeoNodeProxyIdGraphqlWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoNodeProxyIdGraphqlRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Query the GraphQL endpoint of an existing Geo site
     * Queries the GraphQL endpoint of an existing Geo site.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 GeoNode Not Found
     * <p><b>500</b> - 500 Internal Server Error
     * <p><b>400</b> - Bad Request
     * @param id The ID of the Geo node
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoNodeProxyIdGraphqlWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4GeoNodeProxyIdGraphqlRequestCreation(id);
    }

    /**
     * Create a Geo node
     * Creates a Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4GeoNodesRequest The postApiV4GeoNodesRequest parameter
     * @return APIEntitiesGeoNode
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoNodesRequestCreation(@jakarta.annotation.Nonnull PostApiV4GeoNodesRequest postApiV4GeoNodesRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4GeoNodesRequest' is set
        if (postApiV4GeoNodesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GeoNodesRequest' when calling postApiV4GeoNodes", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGeoNode> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_nodes", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GeoNodesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a Geo node
     * Creates a Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4GeoNodesRequest The postApiV4GeoNodesRequest parameter
     * @return APIEntitiesGeoNode
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoNode postApiV4GeoNodes(@jakarta.annotation.Nonnull PostApiV4GeoNodesRequest postApiV4GeoNodesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNode> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoNodesRequestCreation(postApiV4GeoNodesRequest).body(localVarReturnType);
    }

    /**
     * Create a Geo node
     * Creates a Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4GeoNodesRequest The postApiV4GeoNodesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGeoNode&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoNode> postApiV4GeoNodesWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4GeoNodesRequest postApiV4GeoNodesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNode> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoNodesRequestCreation(postApiV4GeoNodesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a Geo node
     * Creates a Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4GeoNodesRequest The postApiV4GeoNodesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoNodesWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4GeoNodesRequest postApiV4GeoNodesRequest) throws RestClientResponseException {
        return postApiV4GeoNodesRequestCreation(postApiV4GeoNodesRequest);
    }

    /**
     * Repair authentication of the Geo node
     * Repairs the OAuth authentication of a primary Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @return APIEntitiesGeoNodeStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoNodesIdRepairRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GeoNodesIdRepair", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGeoNodeStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_nodes/{id}/repair", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Repair authentication of the Geo node
     * Repairs the OAuth authentication of a primary Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @return APIEntitiesGeoNodeStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoNodeStatus postApiV4GeoNodesIdRepair(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNodeStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoNodesIdRepairRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Repair authentication of the Geo node
     * Repairs the OAuth authentication of a primary Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @return ResponseEntity&lt;APIEntitiesGeoNodeStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoNodeStatus> postApiV4GeoNodesIdRepairWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNodeStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoNodesIdRepairRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Repair authentication of the Geo node
     * Repairs the OAuth authentication of a primary Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoNodesIdRepairWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4GeoNodesIdRepairRequestCreation(id);
    }

    /**
     * Proxy a git-receive-pack info/refs request to the primary site
     * Proxies a git-receive-pack info/refs request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshInfoRefsReceivePackRequest The postApiV4GeoProxyGitSshInfoRefsReceivePackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoProxyGitSshInfoRefsReceivePackRequestCreation(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshInfoRefsReceivePackRequest postApiV4GeoProxyGitSshInfoRefsReceivePackRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4GeoProxyGitSshInfoRefsReceivePackRequest' is set
        if (postApiV4GeoProxyGitSshInfoRefsReceivePackRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GeoProxyGitSshInfoRefsReceivePackRequest' when calling postApiV4GeoProxyGitSshInfoRefsReceivePack", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/geo/proxy_git_ssh/info_refs_receive_pack", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GeoProxyGitSshInfoRefsReceivePackRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Proxy a git-receive-pack info/refs request to the primary site
     * Proxies a git-receive-pack info/refs request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshInfoRefsReceivePackRequest The postApiV4GeoProxyGitSshInfoRefsReceivePackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GeoProxyGitSshInfoRefsReceivePack(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshInfoRefsReceivePackRequest postApiV4GeoProxyGitSshInfoRefsReceivePackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GeoProxyGitSshInfoRefsReceivePackRequestCreation(postApiV4GeoProxyGitSshInfoRefsReceivePackRequest).body(localVarReturnType);
    }

    /**
     * Proxy a git-receive-pack info/refs request to the primary site
     * Proxies a git-receive-pack info/refs request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshInfoRefsReceivePackRequest The postApiV4GeoProxyGitSshInfoRefsReceivePackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GeoProxyGitSshInfoRefsReceivePackWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshInfoRefsReceivePackRequest postApiV4GeoProxyGitSshInfoRefsReceivePackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoProxyGitSshInfoRefsReceivePackRequestCreation(postApiV4GeoProxyGitSshInfoRefsReceivePackRequest).toEntity(localVarReturnType);
    }

    /**
     * Proxy a git-receive-pack info/refs request to the primary site
     * Proxies a git-receive-pack info/refs request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshInfoRefsReceivePackRequest The postApiV4GeoProxyGitSshInfoRefsReceivePackRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoProxyGitSshInfoRefsReceivePackWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshInfoRefsReceivePackRequest postApiV4GeoProxyGitSshInfoRefsReceivePackRequest) throws RestClientResponseException {
        return postApiV4GeoProxyGitSshInfoRefsReceivePackRequestCreation(postApiV4GeoProxyGitSshInfoRefsReceivePackRequest);
    }

    /**
     * Proxy a git-upload-pack info/refs request to the primary site
     * Proxies a git-upload-pack info/refs request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshInfoRefsUploadPackRequest The postApiV4GeoProxyGitSshInfoRefsUploadPackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoProxyGitSshInfoRefsUploadPackRequestCreation(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshInfoRefsUploadPackRequest postApiV4GeoProxyGitSshInfoRefsUploadPackRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4GeoProxyGitSshInfoRefsUploadPackRequest' is set
        if (postApiV4GeoProxyGitSshInfoRefsUploadPackRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GeoProxyGitSshInfoRefsUploadPackRequest' when calling postApiV4GeoProxyGitSshInfoRefsUploadPack", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/geo/proxy_git_ssh/info_refs_upload_pack", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GeoProxyGitSshInfoRefsUploadPackRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Proxy a git-upload-pack info/refs request to the primary site
     * Proxies a git-upload-pack info/refs request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshInfoRefsUploadPackRequest The postApiV4GeoProxyGitSshInfoRefsUploadPackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GeoProxyGitSshInfoRefsUploadPack(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshInfoRefsUploadPackRequest postApiV4GeoProxyGitSshInfoRefsUploadPackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GeoProxyGitSshInfoRefsUploadPackRequestCreation(postApiV4GeoProxyGitSshInfoRefsUploadPackRequest).body(localVarReturnType);
    }

    /**
     * Proxy a git-upload-pack info/refs request to the primary site
     * Proxies a git-upload-pack info/refs request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshInfoRefsUploadPackRequest The postApiV4GeoProxyGitSshInfoRefsUploadPackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GeoProxyGitSshInfoRefsUploadPackWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshInfoRefsUploadPackRequest postApiV4GeoProxyGitSshInfoRefsUploadPackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoProxyGitSshInfoRefsUploadPackRequestCreation(postApiV4GeoProxyGitSshInfoRefsUploadPackRequest).toEntity(localVarReturnType);
    }

    /**
     * Proxy a git-upload-pack info/refs request to the primary site
     * Proxies a git-upload-pack info/refs request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshInfoRefsUploadPackRequest The postApiV4GeoProxyGitSshInfoRefsUploadPackRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoProxyGitSshInfoRefsUploadPackWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshInfoRefsUploadPackRequest postApiV4GeoProxyGitSshInfoRefsUploadPackRequest) throws RestClientResponseException {
        return postApiV4GeoProxyGitSshInfoRefsUploadPackRequestCreation(postApiV4GeoProxyGitSshInfoRefsUploadPackRequest);
    }

    /**
     * Proxy a git-receive-pack request to the primary site
     * Proxies a git-receive-pack request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshReceivePackRequest The postApiV4GeoProxyGitSshReceivePackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoProxyGitSshReceivePackRequestCreation(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshReceivePackRequest postApiV4GeoProxyGitSshReceivePackRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4GeoProxyGitSshReceivePackRequest' is set
        if (postApiV4GeoProxyGitSshReceivePackRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GeoProxyGitSshReceivePackRequest' when calling postApiV4GeoProxyGitSshReceivePack", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/geo/proxy_git_ssh/receive_pack", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GeoProxyGitSshReceivePackRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Proxy a git-receive-pack request to the primary site
     * Proxies a git-receive-pack request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshReceivePackRequest The postApiV4GeoProxyGitSshReceivePackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GeoProxyGitSshReceivePack(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshReceivePackRequest postApiV4GeoProxyGitSshReceivePackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GeoProxyGitSshReceivePackRequestCreation(postApiV4GeoProxyGitSshReceivePackRequest).body(localVarReturnType);
    }

    /**
     * Proxy a git-receive-pack request to the primary site
     * Proxies a git-receive-pack request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshReceivePackRequest The postApiV4GeoProxyGitSshReceivePackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GeoProxyGitSshReceivePackWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshReceivePackRequest postApiV4GeoProxyGitSshReceivePackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoProxyGitSshReceivePackRequestCreation(postApiV4GeoProxyGitSshReceivePackRequest).toEntity(localVarReturnType);
    }

    /**
     * Proxy a git-receive-pack request to the primary site
     * Proxies a git-receive-pack request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshReceivePackRequest The postApiV4GeoProxyGitSshReceivePackRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoProxyGitSshReceivePackWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshReceivePackRequest postApiV4GeoProxyGitSshReceivePackRequest) throws RestClientResponseException {
        return postApiV4GeoProxyGitSshReceivePackRequestCreation(postApiV4GeoProxyGitSshReceivePackRequest);
    }

    /**
     * Proxy a git-upload-pack request to the primary site
     * Proxies a git-upload-pack request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshUploadPackRequest The postApiV4GeoProxyGitSshUploadPackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoProxyGitSshUploadPackRequestCreation(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshUploadPackRequest postApiV4GeoProxyGitSshUploadPackRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4GeoProxyGitSshUploadPackRequest' is set
        if (postApiV4GeoProxyGitSshUploadPackRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GeoProxyGitSshUploadPackRequest' when calling postApiV4GeoProxyGitSshUploadPack", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/geo/proxy_git_ssh/upload_pack", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GeoProxyGitSshUploadPackRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Proxy a git-upload-pack request to the primary site
     * Proxies a git-upload-pack request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshUploadPackRequest The postApiV4GeoProxyGitSshUploadPackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4GeoProxyGitSshUploadPack(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshUploadPackRequest postApiV4GeoProxyGitSshUploadPackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4GeoProxyGitSshUploadPackRequestCreation(postApiV4GeoProxyGitSshUploadPackRequest).body(localVarReturnType);
    }

    /**
     * Proxy a git-upload-pack request to the primary site
     * Proxies a git-upload-pack request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshUploadPackRequest The postApiV4GeoProxyGitSshUploadPackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4GeoProxyGitSshUploadPackWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshUploadPackRequest postApiV4GeoProxyGitSshUploadPackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoProxyGitSshUploadPackRequestCreation(postApiV4GeoProxyGitSshUploadPackRequest).toEntity(localVarReturnType);
    }

    /**
     * Proxy a git-upload-pack request to the primary site
     * Proxies a git-upload-pack request from a secondary site to the primary site.
     * <p><b>200</b> - OK
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4GeoProxyGitSshUploadPackRequest The postApiV4GeoProxyGitSshUploadPackRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoProxyGitSshUploadPackWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4GeoProxyGitSshUploadPackRequest postApiV4GeoProxyGitSshUploadPackRequest) throws RestClientResponseException {
        return postApiV4GeoProxyGitSshUploadPackRequestCreation(postApiV4GeoProxyGitSshUploadPackRequest);
    }

    /**
     * Create a Geo site
     * Creates a Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4GeoSitesRequest The postApiV4GeoSitesRequest parameter
     * @return APIEntitiesGeoSite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoSitesRequestCreation(@jakarta.annotation.Nonnull PostApiV4GeoSitesRequest postApiV4GeoSitesRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4GeoSitesRequest' is set
        if (postApiV4GeoSitesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GeoSitesRequest' when calling postApiV4GeoSites", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGeoSite> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_sites", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GeoSitesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a Geo site
     * Creates a Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4GeoSitesRequest The postApiV4GeoSitesRequest parameter
     * @return APIEntitiesGeoSite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoSite postApiV4GeoSites(@jakarta.annotation.Nonnull PostApiV4GeoSitesRequest postApiV4GeoSitesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSite> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoSitesRequestCreation(postApiV4GeoSitesRequest).body(localVarReturnType);
    }

    /**
     * Create a Geo site
     * Creates a Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4GeoSitesRequest The postApiV4GeoSitesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGeoSite&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoSite> postApiV4GeoSitesWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4GeoSitesRequest postApiV4GeoSitesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSite> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoSitesRequestCreation(postApiV4GeoSitesRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a Geo site
     * Creates a Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * @param postApiV4GeoSitesRequest The postApiV4GeoSitesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoSitesWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4GeoSitesRequest postApiV4GeoSitesRequest) throws RestClientResponseException {
        return postApiV4GeoSitesRequestCreation(postApiV4GeoSitesRequest);
    }

    /**
     * Repair a Geo site
     * Repairs the OAuth authentication of a Geo site if there is an OAuth synchronization problem between the primary or secondary Geo sites. In that case, the message &#x60;There are no OAuth application defined for this Geo node.&#x60; might be displayed.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @return APIEntitiesGeoSiteStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoSitesIdRepairRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GeoSitesIdRepair", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGeoSiteStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_sites/{id}/repair", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Repair a Geo site
     * Repairs the OAuth authentication of a Geo site if there is an OAuth synchronization problem between the primary or secondary Geo sites. In that case, the message &#x60;There are no OAuth application defined for this Geo node.&#x60; might be displayed.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @return APIEntitiesGeoSiteStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoSiteStatus postApiV4GeoSitesIdRepair(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSiteStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoSitesIdRepairRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Repair a Geo site
     * Repairs the OAuth authentication of a Geo site if there is an OAuth synchronization problem between the primary or secondary Geo sites. In that case, the message &#x60;There are no OAuth application defined for this Geo node.&#x60; might be displayed.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @return ResponseEntity&lt;APIEntitiesGeoSiteStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoSiteStatus> postApiV4GeoSitesIdRepairWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSiteStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoSitesIdRepairRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Repair a Geo site
     * Repairs the OAuth authentication of a Geo site if there is an OAuth synchronization problem between the primary or secondary Geo sites. In that case, the message &#x60;There are no OAuth application defined for this Geo node.&#x60; might be displayed.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoSitesIdRepairWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4GeoSitesIdRepairRequestCreation(id);
    }

    /**
     * Create a status for a secondary site
     * Creates a status for a secondary Geo site on the primary site, including replication metrics.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * @param postApiV4GeoStatusRequest The postApiV4GeoStatusRequest parameter
     * @return APIEntitiesGeoNodeStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GeoStatusRequestCreation(@jakarta.annotation.Nullable PostApiV4GeoStatusRequest postApiV4GeoStatusRequest) throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesGeoNodeStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo/status", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GeoStatusRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a status for a secondary site
     * Creates a status for a secondary Geo site on the primary site, including replication metrics.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * @param postApiV4GeoStatusRequest The postApiV4GeoStatusRequest parameter
     * @return APIEntitiesGeoNodeStatus
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoNodeStatus postApiV4GeoStatus(@jakarta.annotation.Nullable PostApiV4GeoStatusRequest postApiV4GeoStatusRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNodeStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoStatusRequestCreation(postApiV4GeoStatusRequest).body(localVarReturnType);
    }

    /**
     * Create a status for a secondary site
     * Creates a status for a secondary Geo site on the primary site, including replication metrics.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * @param postApiV4GeoStatusRequest The postApiV4GeoStatusRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGeoNodeStatus&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoNodeStatus> postApiV4GeoStatusWithHttpInfo(@jakarta.annotation.Nullable PostApiV4GeoStatusRequest postApiV4GeoStatusRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNodeStatus> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GeoStatusRequestCreation(postApiV4GeoStatusRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a status for a secondary site
     * Creates a status for a secondary Geo site on the primary site, including replication metrics.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad Request
     * <p><b>401</b> - 401 Unauthorized
     * @param postApiV4GeoStatusRequest The postApiV4GeoStatusRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GeoStatusWithResponseSpec(@jakarta.annotation.Nullable PostApiV4GeoStatusRequest postApiV4GeoStatusRequest) throws RestClientResponseException {
        return postApiV4GeoStatusRequestCreation(postApiV4GeoStatusRequest);
    }

    /**
     * Update a Geo node
     * Updates a specified Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @param putApiV4GeoNodesIdRequest The putApiV4GeoNodesIdRequest parameter
     * @return APIEntitiesGeoNode
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GeoNodesIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4GeoNodesIdRequest putApiV4GeoNodesIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GeoNodesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGeoNode> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_nodes/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GeoNodesIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a Geo node
     * Updates a specified Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @param putApiV4GeoNodesIdRequest The putApiV4GeoNodesIdRequest parameter
     * @return APIEntitiesGeoNode
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoNode putApiV4GeoNodesId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4GeoNodesIdRequest putApiV4GeoNodesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNode> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GeoNodesIdRequestCreation(id, putApiV4GeoNodesIdRequest).body(localVarReturnType);
    }

    /**
     * Update a Geo node
     * Updates a specified Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @param putApiV4GeoNodesIdRequest The putApiV4GeoNodesIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGeoNode&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoNode> putApiV4GeoNodesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4GeoNodesIdRequest putApiV4GeoNodesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoNode> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GeoNodesIdRequestCreation(id, putApiV4GeoNodesIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a Geo node
     * Updates a specified Geo node.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoNode Not Found
     * @param id The ID of the node
     * @param putApiV4GeoNodesIdRequest The putApiV4GeoNodesIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GeoNodesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4GeoNodesIdRequest putApiV4GeoNodesIdRequest) throws RestClientResponseException {
        return putApiV4GeoNodesIdRequestCreation(id, putApiV4GeoNodesIdRequest);
    }

    /**
     * Update a Geo site
     * Updates a specified Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @param putApiV4GeoSitesIdRequest The putApiV4GeoSitesIdRequest parameter
     * @return APIEntitiesGeoSite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GeoSitesIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4GeoSitesIdRequest putApiV4GeoSitesIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GeoSitesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesGeoSite> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/geo_sites/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GeoSitesIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a Geo site
     * Updates a specified Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @param putApiV4GeoSitesIdRequest The putApiV4GeoSitesIdRequest parameter
     * @return APIEntitiesGeoSite
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesGeoSite putApiV4GeoSitesId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4GeoSitesIdRequest putApiV4GeoSitesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSite> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GeoSitesIdRequestCreation(id, putApiV4GeoSitesIdRequest).body(localVarReturnType);
    }

    /**
     * Update a Geo site
     * Updates a specified Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @param putApiV4GeoSitesIdRequest The putApiV4GeoSitesIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesGeoSite&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesGeoSite> putApiV4GeoSitesIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4GeoSitesIdRequest putApiV4GeoSitesIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesGeoSite> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GeoSitesIdRequestCreation(id, putApiV4GeoSitesIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a Geo site
     * Updates a specified Geo site.
     * <p><b>200</b> - OK
     * <p><b>400</b> - 400 Bad request
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>403</b> - 403 Forbidden
     * <p><b>404</b> - 404 GeoSite Not Found
     * @param id The ID of the site
     * @param putApiV4GeoSitesIdRequest The putApiV4GeoSitesIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GeoSitesIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4GeoSitesIdRequest putApiV4GeoSitesIdRequest) throws RestClientResponseException {
        return putApiV4GeoSitesIdRequestCreation(id, putApiV4GeoSitesIdRequest);
    }
}
