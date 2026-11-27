package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesClustersAgent;
import ai.gebo.gitlab.client.model.APIEntitiesClustersAgentToken;
import ai.gebo.gitlab.client.model.APIEntitiesClustersAgentTokenBasic;
import ai.gebo.gitlab.client.model.APIEntitiesClustersAgentTokenWithToken;
import ai.gebo.gitlab.client.model.APIEntitiesClustersAgentUrlConfiguration;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdClusterAgentsAgentIdTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdClusterAgentsRequest;

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
public class ClusterAgentsApi {
    private ApiClient apiClient;

    public ClusterAgentsApi() {
        this(new ApiClient());
    }

    public ClusterAgentsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an agent
     * Deletes an existing agent registration. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdClusterAgentsAgentIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdClusterAgentsAgentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling deleteApiV4ProjectsIdClusterAgentsAgentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an agent
     * Deletes an existing agent registration. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdClusterAgentsAgentId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdClusterAgentsAgentIdRequestCreation(id, agentId).body(localVarReturnType);
    }

    /**
     * Delete an agent
     * Deletes an existing agent registration. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdClusterAgentsAgentIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdClusterAgentsAgentIdRequestCreation(id, agentId).toEntity(localVarReturnType);
    }

    /**
     * Delete an agent
     * Deletes an existing agent registration. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdClusterAgentsAgentIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdClusterAgentsAgentIdRequestCreation(id, agentId);
    }

    /**
     * Revoke an agent token
     * Revokes an agent token. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param tokenId ID of the agent token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);
        pathParams.put("token_id", tokenId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}/tokens/{token_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Revoke an agent token
     * Revokes an agent token. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param tokenId ID of the agent token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdRequestCreation(id, agentId, tokenId).body(localVarReturnType);
    }

    /**
     * Revoke an agent token
     * Revokes an agent token. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param tokenId ID of the agent token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdRequestCreation(id, agentId, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Revoke an agent token
     * Revokes an agent token. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param tokenId ID of the agent token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdRequestCreation(id, agentId, tokenId);
    }

    /**
     * Delete an agent URL configuration
     * Deletes an agent URL configuration. You must have the Maintainer or Owner role to use this endpoint. This feature was introduced in GitLab 17.4.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param urlConfigurationId ID of the agent URL configuration.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer urlConfigurationId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'urlConfigurationId' is set
        if (urlConfigurationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'urlConfigurationId' when calling deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);
        pathParams.put("url_configuration_id", urlConfigurationId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}/url_configurations/{url_configuration_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an agent URL configuration
     * Deletes an agent URL configuration. You must have the Maintainer or Owner role to use this endpoint. This feature was introduced in GitLab 17.4.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param urlConfigurationId ID of the agent URL configuration.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer urlConfigurationId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdRequestCreation(id, agentId, urlConfigurationId).body(localVarReturnType);
    }

    /**
     * Delete an agent URL configuration
     * Deletes an agent URL configuration. You must have the Maintainer or Owner role to use this endpoint. This feature was introduced in GitLab 17.4.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param urlConfigurationId ID of the agent URL configuration.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer urlConfigurationId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdRequestCreation(id, agentId, urlConfigurationId).toEntity(localVarReturnType);
    }

    /**
     * Delete an agent URL configuration
     * Deletes an agent URL configuration. You must have the Maintainer or Owner role to use this endpoint. This feature was introduced in GitLab 17.4.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param urlConfigurationId ID of the agent URL configuration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer urlConfigurationId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdRequestCreation(id, agentId, urlConfigurationId);
    }

    /**
     * List all agents
     * Lists all agents registered for the project. You must have the Developer, Maintainer, or Owner role to use this endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesClustersAgent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdClusterAgentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdClusterAgents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesClustersAgent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all agents
     * Lists all agents registered for the project. You must have the Developer, Maintainer, or Owner role to use this endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesClustersAgent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesClustersAgent> getApiV4ProjectsIdClusterAgents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesClustersAgent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all agents
     * Lists all agents registered for the project. You must have the Developer, Maintainer, or Owner role to use this endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesClustersAgent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesClustersAgent>> getApiV4ProjectsIdClusterAgentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesClustersAgent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all agents
     * Lists all agents registered for the project. You must have the Developer, Maintainer, or Owner role to use this endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdClusterAgentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdClusterAgentsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve details on an agent
     * Retrieves details on a specified agent. You must have the Developer, Maintainer, or Owner role to use this endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @return APIEntitiesClustersAgent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdClusterAgentsAgentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling getApiV4ProjectsIdClusterAgentsAgentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);

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

        ParameterizedTypeReference<APIEntitiesClustersAgent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve details on an agent
     * Retrieves details on a specified agent. You must have the Developer, Maintainer, or Owner role to use this endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @return APIEntitiesClustersAgent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClustersAgent getApiV4ProjectsIdClusterAgentsAgentId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdRequestCreation(id, agentId).body(localVarReturnType);
    }

    /**
     * Retrieve details on an agent
     * Retrieves details on a specified agent. You must have the Developer, Maintainer, or Owner role to use this endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @return ResponseEntity&lt;APIEntitiesClustersAgent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClustersAgent> getApiV4ProjectsIdClusterAgentsAgentIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdRequestCreation(id, agentId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve details on an agent
     * Retrieves details on a specified agent. You must have the Developer, Maintainer, or Owner role to use this endpoint.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        return getApiV4ProjectsIdClusterAgentsAgentIdRequestCreation(id, agentId);
    }

    /**
     * List all agent tokens
     * Lists all active tokens for an agent. You must have the Developer, Maintainer, or Owner role to use this endpoint. The &#x60;last_used_at&#x60; field is returned only when retrieving a single agent token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesClustersAgentTokenBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdClusterAgentsAgentIdTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling getApiV4ProjectsIdClusterAgentsAgentIdTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);

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

        ParameterizedTypeReference<List<APIEntitiesClustersAgentTokenBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}/tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all agent tokens
     * Lists all active tokens for an agent. You must have the Developer, Maintainer, or Owner role to use this endpoint. The &#x60;last_used_at&#x60; field is returned only when retrieving a single agent token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesClustersAgentTokenBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesClustersAgentTokenBasic> getApiV4ProjectsIdClusterAgentsAgentIdTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesClustersAgentTokenBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdTokensRequestCreation(id, agentId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all agent tokens
     * Lists all active tokens for an agent. You must have the Developer, Maintainer, or Owner role to use this endpoint. The &#x60;last_used_at&#x60; field is returned only when retrieving a single agent token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesClustersAgentTokenBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesClustersAgentTokenBasic>> getApiV4ProjectsIdClusterAgentsAgentIdTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesClustersAgentTokenBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdTokensRequestCreation(id, agentId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all agent tokens
     * Lists all active tokens for an agent. You must have the Developer, Maintainer, or Owner role to use this endpoint. The &#x60;last_used_at&#x60; field is returned only when retrieving a single agent token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdClusterAgentsAgentIdTokensRequestCreation(id, agentId, page, perPage);
    }

    /**
     * Retrieve an agent token
     * Retrieves a specified agent token. You must have the Developer, Maintainer, or Owner role to use this endpoint. Returns a &#x60;404&#x60; if the agent token has been revoked.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param tokenId ID of the agent token.
     * @return APIEntitiesClustersAgentToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);
        pathParams.put("token_id", tokenId);

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

        ParameterizedTypeReference<APIEntitiesClustersAgentToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}/tokens/{token_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an agent token
     * Retrieves a specified agent token. You must have the Developer, Maintainer, or Owner role to use this endpoint. Returns a &#x60;404&#x60; if the agent token has been revoked.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param tokenId ID of the agent token.
     * @return APIEntitiesClustersAgentToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClustersAgentToken getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgentToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdRequestCreation(id, agentId, tokenId).body(localVarReturnType);
    }

    /**
     * Retrieve an agent token
     * Retrieves a specified agent token. You must have the Developer, Maintainer, or Owner role to use this endpoint. Returns a &#x60;404&#x60; if the agent token has been revoked.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param tokenId ID of the agent token.
     * @return ResponseEntity&lt;APIEntitiesClustersAgentToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClustersAgentToken> getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgentToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdRequestCreation(id, agentId, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an agent token
     * Retrieves a specified agent token. You must have the Developer, Maintainer, or Owner role to use this endpoint. Returns a &#x60;404&#x60; if the agent token has been revoked.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param tokenId ID of the agent token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        return getApiV4ProjectsIdClusterAgentsAgentIdTokensTokenIdRequestCreation(id, agentId, tokenId);
    }

    /**
     * List all agent URL configurations
     * Lists all agent URL configurations for a specified agent. You must have the Developer, Maintainer, or Owner role to use this endpoint. Either &#x60;public_key&#x60; or &#x60;client_cert&#x60; is set, but never both. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @return List&lt;APIEntitiesClustersAgentUrlConfiguration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);

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

        ParameterizedTypeReference<List<APIEntitiesClustersAgentUrlConfiguration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}/url_configurations", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all agent URL configurations
     * Lists all agent URL configurations for a specified agent. You must have the Developer, Maintainer, or Owner role to use this endpoint. Either &#x60;public_key&#x60; or &#x60;client_cert&#x60; is set, but never both. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @return List&lt;APIEntitiesClustersAgentUrlConfiguration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesClustersAgentUrlConfiguration> getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurations(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesClustersAgentUrlConfiguration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequestCreation(id, agentId).body(localVarReturnType);
    }

    /**
     * List all agent URL configurations
     * Lists all agent URL configurations for a specified agent. You must have the Developer, Maintainer, or Owner role to use this endpoint. Either &#x60;public_key&#x60; or &#x60;client_cert&#x60; is set, but never both. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @return ResponseEntity&lt;List&lt;APIEntitiesClustersAgentUrlConfiguration&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesClustersAgentUrlConfiguration>> getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesClustersAgentUrlConfiguration>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequestCreation(id, agentId).toEntity(localVarReturnType);
    }

    /**
     * List all agent URL configurations
     * Lists all agent URL configurations for a specified agent. You must have the Developer, Maintainer, or Owner role to use this endpoint. Either &#x60;public_key&#x60; or &#x60;client_cert&#x60; is set, but never both. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId) throws RestClientResponseException {
        return getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequestCreation(id, agentId);
    }

    /**
     * Retrieve an agent URL configuration
     * Retrieves an agent URL configuration. You must have the Developer, Maintainer, or Owner role to use this endpoint. Either &#x60;public_key&#x60; or &#x60;client_cert&#x60; is set, but never both. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param urlConfigurationId ID of the agent URL configuration.
     * @return APIEntitiesClustersAgentUrlConfiguration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer urlConfigurationId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'urlConfigurationId' is set
        if (urlConfigurationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'urlConfigurationId' when calling getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);
        pathParams.put("url_configuration_id", urlConfigurationId);

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

        ParameterizedTypeReference<APIEntitiesClustersAgentUrlConfiguration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}/url_configurations/{url_configuration_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an agent URL configuration
     * Retrieves an agent URL configuration. You must have the Developer, Maintainer, or Owner role to use this endpoint. Either &#x60;public_key&#x60; or &#x60;client_cert&#x60; is set, but never both. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param urlConfigurationId ID of the agent URL configuration.
     * @return APIEntitiesClustersAgentUrlConfiguration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClustersAgentUrlConfiguration getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer urlConfigurationId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgentUrlConfiguration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdRequestCreation(id, agentId, urlConfigurationId).body(localVarReturnType);
    }

    /**
     * Retrieve an agent URL configuration
     * Retrieves an agent URL configuration. You must have the Developer, Maintainer, or Owner role to use this endpoint. Either &#x60;public_key&#x60; or &#x60;client_cert&#x60; is set, but never both. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param urlConfigurationId ID of the agent URL configuration.
     * @return ResponseEntity&lt;APIEntitiesClustersAgentUrlConfiguration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClustersAgentUrlConfiguration> getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer urlConfigurationId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgentUrlConfiguration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdRequestCreation(id, agentId, urlConfigurationId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an agent URL configuration
     * Retrieves an agent URL configuration. You must have the Developer, Maintainer, or Owner role to use this endpoint. Either &#x60;public_key&#x60; or &#x60;client_cert&#x60; is set, but never both. This feature was introduced in GitLab 17.4.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param urlConfigurationId ID of the agent URL configuration.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull Integer urlConfigurationId) throws RestClientResponseException {
        return getApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsUrlConfigurationIdRequestCreation(id, agentId, urlConfigurationId);
    }

    /**
     * Create an agent
     * Creates an agent for the project. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdClusterAgentsRequest The postApiV4ProjectsIdClusterAgentsRequest parameter
     * @return APIEntitiesClustersAgent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdClusterAgentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsRequest postApiV4ProjectsIdClusterAgentsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdClusterAgents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdClusterAgentsRequest' is set
        if (postApiV4ProjectsIdClusterAgentsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdClusterAgentsRequest' when calling postApiV4ProjectsIdClusterAgents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesClustersAgent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdClusterAgentsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an agent
     * Creates an agent for the project. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdClusterAgentsRequest The postApiV4ProjectsIdClusterAgentsRequest parameter
     * @return APIEntitiesClustersAgent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClustersAgent postApiV4ProjectsIdClusterAgents(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsRequest postApiV4ProjectsIdClusterAgentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdClusterAgentsRequestCreation(id, postApiV4ProjectsIdClusterAgentsRequest).body(localVarReturnType);
    }

    /**
     * Create an agent
     * Creates an agent for the project. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdClusterAgentsRequest The postApiV4ProjectsIdClusterAgentsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesClustersAgent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClustersAgent> postApiV4ProjectsIdClusterAgentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsRequest postApiV4ProjectsIdClusterAgentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdClusterAgentsRequestCreation(id, postApiV4ProjectsIdClusterAgentsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an agent
     * Creates an agent for the project. You must have the Maintainer or Owner role to use this endpoint.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdClusterAgentsRequest The postApiV4ProjectsIdClusterAgentsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdClusterAgentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsRequest postApiV4ProjectsIdClusterAgentsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdClusterAgentsRequestCreation(id, postApiV4ProjectsIdClusterAgentsRequest);
    }

    /**
     * Create an agent token
     * Creates a token for an agent. You must have the Maintainer or Owner role to use this endpoint. An agent can have only two active tokens at one time. The &#x60;token&#x60; value is returned only in this response and cannot be retrieved later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest The postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest parameter
     * @return APIEntitiesClustersAgentTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdClusterAgentsAgentIdTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsAgentIdTokensRequest postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdClusterAgentsAgentIdTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling postApiV4ProjectsIdClusterAgentsAgentIdTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest' is set
        if (postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest' when calling postApiV4ProjectsIdClusterAgentsAgentIdTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);

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

        ParameterizedTypeReference<APIEntitiesClustersAgentTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}/tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an agent token
     * Creates a token for an agent. You must have the Maintainer or Owner role to use this endpoint. An agent can have only two active tokens at one time. The &#x60;token&#x60; value is returned only in this response and cannot be retrieved later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest The postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest parameter
     * @return APIEntitiesClustersAgentTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClustersAgentTokenWithToken postApiV4ProjectsIdClusterAgentsAgentIdTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsAgentIdTokensRequest postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgentTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdClusterAgentsAgentIdTokensRequestCreation(id, agentId, postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest).body(localVarReturnType);
    }

    /**
     * Create an agent token
     * Creates a token for an agent. You must have the Maintainer or Owner role to use this endpoint. An agent can have only two active tokens at one time. The &#x60;token&#x60; value is returned only in this response and cannot be retrieved later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest The postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesClustersAgentTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClustersAgentTokenWithToken> postApiV4ProjectsIdClusterAgentsAgentIdTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsAgentIdTokensRequest postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgentTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdClusterAgentsAgentIdTokensRequestCreation(id, agentId, postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an agent token
     * Creates a token for an agent. You must have the Maintainer or Owner role to use this endpoint. An agent can have only two active tokens at one time. The &#x60;token&#x60; value is returned only in this response and cannot be retrieved later.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest The postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdClusterAgentsAgentIdTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsAgentIdTokensRequest postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdClusterAgentsAgentIdTokensRequestCreation(id, agentId, postApiV4ProjectsIdClusterAgentsAgentIdTokensRequest);
    }

    /**
     * Create an agent URL configuration
     * Creates an agent URL configuration. You must have the Maintainer or Owner role to use this endpoint. An agent can have only one URL configuration at the time. If &#x60;client_cert&#x60; and &#x60;client_key&#x60; are not provided, a private-public key pair is generated and JWT authentication is used instead of mTLS. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest The postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest parameter
     * @return APIEntitiesClustersAgentUrlConfiguration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'agentId' when calling postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest' is set
        if (postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest' when calling postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurations", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("agent_id", agentId);

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

        ParameterizedTypeReference<APIEntitiesClustersAgentUrlConfiguration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/cluster_agents/{agent_id}/url_configurations", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an agent URL configuration
     * Creates an agent URL configuration. You must have the Maintainer or Owner role to use this endpoint. An agent can have only one URL configuration at the time. If &#x60;client_cert&#x60; and &#x60;client_key&#x60; are not provided, a private-public key pair is generated and JWT authentication is used instead of mTLS. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest The postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest parameter
     * @return APIEntitiesClustersAgentUrlConfiguration
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesClustersAgentUrlConfiguration postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurations(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgentUrlConfiguration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequestCreation(id, agentId, postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest).body(localVarReturnType);
    }

    /**
     * Create an agent URL configuration
     * Creates an agent URL configuration. You must have the Maintainer or Owner role to use this endpoint. An agent can have only one URL configuration at the time. If &#x60;client_cert&#x60; and &#x60;client_key&#x60; are not provided, a private-public key pair is generated and JWT authentication is used instead of mTLS. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest The postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesClustersAgentUrlConfiguration&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesClustersAgentUrlConfiguration> postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesClustersAgentUrlConfiguration> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequestCreation(id, agentId, postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an agent URL configuration
     * Creates an agent URL configuration. You must have the Maintainer or Owner role to use this endpoint. An agent can have only one URL configuration at the time. If &#x60;client_cert&#x60; and &#x60;client_key&#x60; are not provided, a private-public key pair is generated and JWT authentication is used instead of mTLS. This feature was introduced in GitLab 17.4.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param agentId ID of the agent.
     * @param postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest The postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer agentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequestCreation(id, agentId, postApiV4ProjectsIdClusterAgentsAgentIdUrlConfigurationsRequest);
    }
}
