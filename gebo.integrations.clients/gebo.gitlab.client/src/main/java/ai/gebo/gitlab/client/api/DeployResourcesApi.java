package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesDeployKey;
import ai.gebo.gitlab.client.model.APIEntitiesDeployKeysProject;
import ai.gebo.gitlab.client.model.APIEntitiesDeployToken;
import ai.gebo.gitlab.client.model.APIEntitiesDeployTokenWithToken;
import ai.gebo.gitlab.client.model.APIEntitiesDeployment;
import ai.gebo.gitlab.client.model.APIEntitiesDeploymentExtended;
import ai.gebo.gitlab.client.model.APIEntitiesDeploymentsApproval;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestBasic;
import ai.gebo.gitlab.client.model.GetApiV4DependencyListExportsExportIdExportIdParameter;
import ai.gebo.gitlab.client.model.GetApiV4MergeRequestsAssigneeIdParameter;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4DeployKeysRequest;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdDeployTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdDeployKeysRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdDeployTokensRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdDeploymentsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdDeployKeysKeyIdRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdDeploymentsDeploymentIdRequest;

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
public class DeployResourcesApi {
    private ApiClient apiClient;

    public DeployResourcesApi() {
        this(new ApiClient());
    }

    public DeployResourcesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a group deploy token
     * Deletes a group deploy token.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the deploy token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdDeployTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdDeployTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling deleteApiV4GroupsIdDeployTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/deploy_tokens/{token_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group deploy token
     * Deletes a group deploy token.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the deploy token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdDeployTokensTokenId(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdDeployTokensTokenIdRequestCreation(id, tokenId).body(localVarReturnType);
    }

    /**
     * Delete a group deploy token
     * Deletes a group deploy token.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the deploy token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdDeployTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdDeployTokensTokenIdRequestCreation(id, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Delete a group deploy token
     * Deletes a group deploy token.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the deploy token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdDeployTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        return deleteApiV4GroupsIdDeployTokensTokenIdRequestCreation(id, tokenId);
    }

    /**
     * Delete a deploy key
     * Deletes a deploy key from the project. If the deploy key is used only for this project, it is deleted from the system.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdDeployKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdDeployKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling deleteApiV4ProjectsIdDeployKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_keys/{key_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a deploy key
     * Deletes a deploy key from the project. If the deploy key is used only for this project, it is deleted from the system.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdDeployKeysKeyId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdDeployKeysKeyIdRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Delete a deploy key
     * Deletes a deploy key from the project. If the deploy key is used only for this project, it is deleted from the system.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdDeployKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdDeployKeysKeyIdRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Delete a deploy key
     * Deletes a deploy key from the project. If the deploy key is used only for this project, it is deleted from the system.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdDeployKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdDeployKeysKeyIdRequestCreation(id, keyId);
    }

    /**
     * Delete a project deploy token
     * Deletes a project deploy token.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the deploy token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdDeployTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdDeployTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling deleteApiV4ProjectsIdDeployTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_tokens/{token_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a project deploy token
     * Deletes a project deploy token.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the deploy token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdDeployTokensTokenId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdDeployTokensTokenIdRequestCreation(id, tokenId).body(localVarReturnType);
    }

    /**
     * Delete a project deploy token
     * Deletes a project deploy token.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the deploy token.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdDeployTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdDeployTokensTokenIdRequestCreation(id, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Delete a project deploy token
     * Deletes a project deploy token.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the deploy token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdDeployTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdDeployTokensTokenIdRequestCreation(id, tokenId);
    }

    /**
     * Delete a deployment
     * Deletes a specified deployment that is not currently the last deployment for an environment or in a &#x60;running&#x60; state.
     * <p><b>204</b> - Deployment destroyed
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - \&quot;Cannot destroy running deployment\&quot; or \&quot;Deployment currently deployed to environment\&quot;
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdDeploymentsDeploymentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'deploymentId' is set
        if (deploymentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'deploymentId' when calling deleteApiV4ProjectsIdDeploymentsDeploymentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("deployment_id", deploymentId);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/deployments/{deployment_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a deployment
     * Deletes a specified deployment that is not currently the last deployment for an environment or in a &#x60;running&#x60; state.
     * <p><b>204</b> - Deployment destroyed
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - \&quot;Cannot destroy running deployment\&quot; or \&quot;Deployment currently deployed to environment\&quot;
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdDeploymentsDeploymentId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(id, deploymentId).body(localVarReturnType);
    }

    /**
     * Delete a deployment
     * Deletes a specified deployment that is not currently the last deployment for an environment or in a &#x60;running&#x60; state.
     * <p><b>204</b> - Deployment destroyed
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - \&quot;Cannot destroy running deployment\&quot; or \&quot;Deployment currently deployed to environment\&quot;
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdDeploymentsDeploymentIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(id, deploymentId).toEntity(localVarReturnType);
    }

    /**
     * Delete a deployment
     * Deletes a specified deployment that is not currently the last deployment for an environment or in a &#x60;running&#x60; state.
     * <p><b>204</b> - Deployment destroyed
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - \&quot;Cannot destroy running deployment\&quot; or \&quot;Deployment currently deployed to environment\&quot;
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdDeploymentsDeploymentIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(id, deploymentId);
    }

    /**
     * List all deploy keys
     * Lists all deploy keys for the instance. Requires administrator access.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param _public If &#x60;true&#x60;, returns only deploy keys that are public.
     * @return List&lt;APIEntitiesDeployKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4DeployKeysRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean _public) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "public", _public));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDeployKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/deploy_keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all deploy keys
     * Lists all deploy keys for the instance. Requires administrator access.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param _public If &#x60;true&#x60;, returns only deploy keys that are public.
     * @return List&lt;APIEntitiesDeployKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDeployKey> getApiV4DeployKeys(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean _public) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DeployKeysRequestCreation(page, perPage, _public).body(localVarReturnType);
    }

    /**
     * List all deploy keys
     * Lists all deploy keys for the instance. Requires administrator access.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param _public If &#x60;true&#x60;, returns only deploy keys that are public.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDeployKey&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDeployKey>> getApiV4DeployKeysWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean _public) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DeployKeysRequestCreation(page, perPage, _public).toEntity(localVarReturnType);
    }

    /**
     * List all deploy keys
     * Lists all deploy keys for the instance. Requires administrator access.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param _public If &#x60;true&#x60;, returns only deploy keys that are public.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4DeployKeysWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean _public) throws RestClientResponseException {
        return getApiV4DeployKeysRequestCreation(page, perPage, _public);
    }

    /**
     * List all deploy tokens
     * Lists all deploy tokens for the instance. Requires administrator access.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return List&lt;APIEntitiesDeployToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4DeployTokensRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDeployToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/deploy_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all deploy tokens
     * Lists all deploy tokens for the instance. Requires administrator access.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return List&lt;APIEntitiesDeployToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDeployToken> getApiV4DeployTokens(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DeployTokensRequestCreation(page, perPage, active).body(localVarReturnType);
    }

    /**
     * List all deploy tokens
     * Lists all deploy tokens for the instance. Requires administrator access.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDeployToken&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDeployToken>> getApiV4DeployTokensWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4DeployTokensRequestCreation(page, perPage, active).toEntity(localVarReturnType);
    }

    /**
     * List all deploy tokens
     * Lists all deploy tokens for the instance. Requires administrator access.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4DeployTokensWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        return getApiV4DeployTokensRequestCreation(page, perPage, active);
    }

    /**
     * List all group deploy tokens
     * Lists all group deploy tokens.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return List&lt;APIEntitiesDeployToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDeployTokensRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDeployTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDeployToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/deploy_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group deploy tokens
     * Lists all group deploy tokens.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return List&lt;APIEntitiesDeployToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDeployToken> getApiV4GroupsIdDeployTokens(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDeployTokensRequestCreation(id, page, perPage, active).body(localVarReturnType);
    }

    /**
     * List all group deploy tokens
     * Lists all group deploy tokens.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDeployToken&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDeployToken>> getApiV4GroupsIdDeployTokensWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDeployTokensRequestCreation(id, page, perPage, active).toEntity(localVarReturnType);
    }

    /**
     * List all group deploy tokens
     * Lists all group deploy tokens.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDeployTokensWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        return getApiV4GroupsIdDeployTokensRequestCreation(id, page, perPage, active);
    }

    /**
     * Retrieve a group deploy token
     * Get a single group&#39;s deploy token by ID. This feature was introduced in GitLab 14.9. 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the deploy token.
     * @return APIEntitiesDeployToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDeployTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDeployTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling getApiV4GroupsIdDeployTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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

        ParameterizedTypeReference<APIEntitiesDeployToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/deploy_tokens/{token_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group deploy token
     * Get a single group&#39;s deploy token by ID. This feature was introduced in GitLab 14.9. 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the deploy token.
     * @return APIEntitiesDeployToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeployToken getApiV4GroupsIdDeployTokensTokenId(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDeployTokensTokenIdRequestCreation(id, tokenId).body(localVarReturnType);
    }

    /**
     * Retrieve a group deploy token
     * Get a single group&#39;s deploy token by ID. This feature was introduced in GitLab 14.9. 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the deploy token.
     * @return ResponseEntity&lt;APIEntitiesDeployToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeployToken> getApiV4GroupsIdDeployTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDeployTokensTokenIdRequestCreation(id, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group deploy token
     * Get a single group&#39;s deploy token by ID. This feature was introduced in GitLab 14.9. 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param tokenId ID of the deploy token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDeployTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        return getApiV4GroupsIdDeployTokensTokenIdRequestCreation(id, tokenId);
    }

    /**
     * List all deploy keys for project
     * Lists all deploy keys for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDeployKeysProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDeployKeysRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDeployKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesDeployKeysProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all deploy keys for project
     * Lists all deploy keys for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDeployKeysProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDeployKeysProject> getApiV4ProjectsIdDeployKeys(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployKeysProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeployKeysRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all deploy keys for project
     * Lists all deploy keys for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDeployKeysProject&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDeployKeysProject>> getApiV4ProjectsIdDeployKeysWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployKeysProject>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeployKeysRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all deploy keys for project
     * Lists all deploy keys for a specified project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDeployKeysWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdDeployKeysRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a deploy key
     * Retrieves a specified deploy key.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @return APIEntitiesDeployKeysProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDeployKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDeployKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling getApiV4ProjectsIdDeployKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesDeployKeysProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_keys/{key_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a deploy key
     * Retrieves a specified deploy key.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @return APIEntitiesDeployKeysProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeployKeysProject getApiV4ProjectsIdDeployKeysKeyId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKeysProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeployKeysKeyIdRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Retrieve a deploy key
     * Retrieves a specified deploy key.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @return ResponseEntity&lt;APIEntitiesDeployKeysProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeployKeysProject> getApiV4ProjectsIdDeployKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKeysProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeployKeysKeyIdRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a deploy key
     * Retrieves a specified deploy key.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDeployKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return getApiV4ProjectsIdDeployKeysKeyIdRequestCreation(id, keyId);
    }

    /**
     * List all project deploy tokens
     * Lists all project deploy tokens.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return List&lt;APIEntitiesDeployToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDeployTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDeployTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "active", active));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDeployToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_tokens", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project deploy tokens
     * Lists all project deploy tokens.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return List&lt;APIEntitiesDeployToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDeployToken> getApiV4ProjectsIdDeployTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeployTokensRequestCreation(id, page, perPage, active).body(localVarReturnType);
    }

    /**
     * List all project deploy tokens
     * Lists all project deploy tokens.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDeployToken&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDeployToken>> getApiV4ProjectsIdDeployTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployToken>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeployTokensRequestCreation(id, page, perPage, active).toEntity(localVarReturnType);
    }

    /**
     * List all project deploy tokens
     * Lists all project deploy tokens.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param active If &#x60;true&#x60;, returns only active deploy tokens.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDeployTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean active) throws RestClientResponseException {
        return getApiV4ProjectsIdDeployTokensRequestCreation(id, page, perPage, active);
    }

    /**
     * Retrieve a project deploy token
     * Retrieves a project deploy token.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the deploy token.
     * @return APIEntitiesDeployToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDeployTokensTokenIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDeployTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'tokenId' is set
        if (tokenId == null) {
            throw new RestClientResponseException("Missing the required parameter 'tokenId' when calling getApiV4ProjectsIdDeployTokensTokenId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
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

        ParameterizedTypeReference<APIEntitiesDeployToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_tokens/{token_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a project deploy token
     * Retrieves a project deploy token.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the deploy token.
     * @return APIEntitiesDeployToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeployToken getApiV4ProjectsIdDeployTokensTokenId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeployTokensTokenIdRequestCreation(id, tokenId).body(localVarReturnType);
    }

    /**
     * Retrieve a project deploy token
     * Retrieves a project deploy token.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the deploy token.
     * @return ResponseEntity&lt;APIEntitiesDeployToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeployToken> getApiV4ProjectsIdDeployTokensTokenIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeployTokensTokenIdRequestCreation(id, tokenId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a project deploy token
     * Retrieves a project deploy token.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param tokenId ID of the deploy token.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDeployTokensTokenIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer tokenId) throws RestClientResponseException {
        return getApiV4ProjectsIdDeployTokensTokenIdRequestCreation(id, tokenId);
    }

    /**
     * List all project deployments
     * Lists all deployments in a project. Some filters, sort orders, and statuses work only in certain combinations. Returns &#x60;400 Bad Request&#x60; when a request filters by &#x60;updated_after&#x60; or &#x60;updated_before&#x60; without &#x60;order_by&#x3D;updated_at&#x60;, filters by &#x60;finished_after&#x60; or &#x60;finished_before&#x60; without both &#x60;order_by&#x3D;finished_at&#x60; and &#x60;status&#x3D;success&#x60;, sets &#x60;order_by&#x3D;finished_at&#x60; without filtering by &#x60;finished_after&#x60;, &#x60;finished_before&#x60;, or a &#x60;status&#x60; of &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60;, or combines &#x60;updated_after&#x60; or &#x60;updated_before&#x60; with &#x60;finished_after&#x60; or &#x60;finished_before&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param updatedAfter Return deployments updated after the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param updatedBefore Return deployments updated before the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param finishedAfter Return deployments finished after the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param finishedBefore Return deployments finished before the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param environment Name of the [environment](https://docs.gitlab.com/ci/environments/) to filter deployments by.
     * @param status Filter deployments by status.
     * @return List&lt;APIEntitiesDeployment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDeploymentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime finishedAfter, @jakarta.annotation.Nullable OffsetDateTime finishedBefore, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDeployments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "finished_after", finishedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "finished_before", finishedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "environment", environment));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesDeployment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deployments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project deployments
     * Lists all deployments in a project. Some filters, sort orders, and statuses work only in certain combinations. Returns &#x60;400 Bad Request&#x60; when a request filters by &#x60;updated_after&#x60; or &#x60;updated_before&#x60; without &#x60;order_by&#x3D;updated_at&#x60;, filters by &#x60;finished_after&#x60; or &#x60;finished_before&#x60; without both &#x60;order_by&#x3D;finished_at&#x60; and &#x60;status&#x3D;success&#x60;, sets &#x60;order_by&#x3D;finished_at&#x60; without filtering by &#x60;finished_after&#x60;, &#x60;finished_before&#x60;, or a &#x60;status&#x60; of &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60;, or combines &#x60;updated_after&#x60; or &#x60;updated_before&#x60; with &#x60;finished_after&#x60; or &#x60;finished_before&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param updatedAfter Return deployments updated after the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param updatedBefore Return deployments updated before the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param finishedAfter Return deployments finished after the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param finishedBefore Return deployments finished before the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param environment Name of the [environment](https://docs.gitlab.com/ci/environments/) to filter deployments by.
     * @param status Filter deployments by status.
     * @return List&lt;APIEntitiesDeployment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDeployment> getApiV4ProjectsIdDeployments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime finishedAfter, @jakarta.annotation.Nullable OffsetDateTime finishedBefore, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeploymentsRequestCreation(id, page, perPage, orderBy, sort, updatedAfter, updatedBefore, finishedAfter, finishedBefore, environment, status).body(localVarReturnType);
    }

    /**
     * List all project deployments
     * Lists all deployments in a project. Some filters, sort orders, and statuses work only in certain combinations. Returns &#x60;400 Bad Request&#x60; when a request filters by &#x60;updated_after&#x60; or &#x60;updated_before&#x60; without &#x60;order_by&#x3D;updated_at&#x60;, filters by &#x60;finished_after&#x60; or &#x60;finished_before&#x60; without both &#x60;order_by&#x3D;finished_at&#x60; and &#x60;status&#x3D;success&#x60;, sets &#x60;order_by&#x3D;finished_at&#x60; without filtering by &#x60;finished_after&#x60;, &#x60;finished_before&#x60;, or a &#x60;status&#x60; of &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60;, or combines &#x60;updated_after&#x60; or &#x60;updated_before&#x60; with &#x60;finished_after&#x60; or &#x60;finished_before&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param updatedAfter Return deployments updated after the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param updatedBefore Return deployments updated before the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param finishedAfter Return deployments finished after the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param finishedBefore Return deployments finished before the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param environment Name of the [environment](https://docs.gitlab.com/ci/environments/) to filter deployments by.
     * @param status Filter deployments by status.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDeployment&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDeployment>> getApiV4ProjectsIdDeploymentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime finishedAfter, @jakarta.annotation.Nullable OffsetDateTime finishedBefore, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeploymentsRequestCreation(id, page, perPage, orderBy, sort, updatedAfter, updatedBefore, finishedAfter, finishedBefore, environment, status).toEntity(localVarReturnType);
    }

    /**
     * List all project deployments
     * Lists all deployments in a project. Some filters, sort orders, and statuses work only in certain combinations. Returns &#x60;400 Bad Request&#x60; when a request filters by &#x60;updated_after&#x60; or &#x60;updated_before&#x60; without &#x60;order_by&#x3D;updated_at&#x60;, filters by &#x60;finished_after&#x60; or &#x60;finished_before&#x60; without both &#x60;order_by&#x3D;finished_at&#x60; and &#x60;status&#x3D;success&#x60;, sets &#x60;order_by&#x3D;finished_at&#x60; without filtering by &#x60;finished_after&#x60;, &#x60;finished_before&#x60;, or a &#x60;status&#x60; of &#x60;success&#x60;, &#x60;failed&#x60;, or &#x60;canceled&#x60;, or combines &#x60;updated_after&#x60; or &#x60;updated_before&#x60; with &#x60;finished_after&#x60; or &#x60;finished_before&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param updatedAfter Return deployments updated after the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param updatedBefore Return deployments updated before the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param finishedAfter Return deployments finished after the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param finishedBefore Return deployments finished before the specified date. Expected in ISO 8601 format (&#x60;2019-03-15T08:00:00Z&#x60;)
     * @param environment Name of the [environment](https://docs.gitlab.com/ci/environments/) to filter deployments by.
     * @param status Filter deployments by status.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDeploymentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime finishedAfter, @jakarta.annotation.Nullable OffsetDateTime finishedBefore, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable String status) throws RestClientResponseException {
        return getApiV4ProjectsIdDeploymentsRequestCreation(id, page, perPage, orderBy, sort, updatedAfter, updatedBefore, finishedAfter, finishedBefore, environment, status);
    }

    /**
     * Retrieve a deployment
     * Retrieves a specified deployment. When multiple approval rules are configured, the response includes the &#x60;approval_summary&#x60; property.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @return APIEntitiesDeploymentExtended
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDeploymentsDeploymentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'deploymentId' is set
        if (deploymentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'deploymentId' when calling getApiV4ProjectsIdDeploymentsDeploymentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("deployment_id", deploymentId);

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

        ParameterizedTypeReference<APIEntitiesDeploymentExtended> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deployments/{deployment_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a deployment
     * Retrieves a specified deployment. When multiple approval rules are configured, the response includes the &#x60;approval_summary&#x60; property.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @return APIEntitiesDeploymentExtended
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeploymentExtended getApiV4ProjectsIdDeploymentsDeploymentId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeploymentExtended> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(id, deploymentId).body(localVarReturnType);
    }

    /**
     * Retrieve a deployment
     * Retrieves a specified deployment. When multiple approval rules are configured, the response includes the &#x60;approval_summary&#x60; property.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @return ResponseEntity&lt;APIEntitiesDeploymentExtended&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeploymentExtended> getApiV4ProjectsIdDeploymentsDeploymentIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeploymentExtended> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(id, deploymentId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a deployment
     * Retrieves a specified deployment. When multiple approval rules are configured, the response includes the &#x60;approval_summary&#x60; property.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDeploymentsDeploymentIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId) throws RestClientResponseException {
        return getApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(id, deploymentId);
    }

    /**
     * List all merge requests associated with a deployment
     * Lists all merge requests shipped with a specified deployment. Not all deployments can be associated with merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdDeploymentsDeploymentIdMergeRequestsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdDeploymentsDeploymentIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'deploymentId' is set
        if (deploymentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'deploymentId' when calling getApiV4ProjectsIdDeploymentsDeploymentIdMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("deployment_id", deploymentId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assignee_id", assigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "assignee_username", assigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reviewer_username", reviewerUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone", milestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "reviewer_id", reviewerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_labels_details", withLabelsDetails));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_merge_status_recheck", withMergeStatusRecheck));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merged_after", mergedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merged_before", mergedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "view", view));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_branch", sourceBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_project_id", sourceProjectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "target_branch", targetBranch));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "wip", wip));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "draft", draft));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[assignee_id]", notAssigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[assignee_username]", notAssigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[reviewer_username]", notReviewerUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone]", notMilestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[my_reaction_emoji]", notMyReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[reviewer_id]", notReviewerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "deployed_before", deployedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "deployed_after", deployedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "environment", environment));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_user_id", mergeUserId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_user_username", mergeUserUsername));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deployments/{deployment_id}/merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge requests associated with a deployment
     * Lists all merge requests shipped with a specified deployment. Not all deployments can be associated with merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestBasic> getApiV4ProjectsIdDeploymentsDeploymentIdMergeRequests(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeploymentsDeploymentIdMergeRequestsRequestCreation(id, deploymentId, page, perPage, authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername).body(localVarReturnType);
    }

    /**
     * List all merge requests associated with a deployment
     * Lists all merge requests shipped with a specified deployment. Not all deployments can be associated with merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestBasic>> getApiV4ProjectsIdDeploymentsDeploymentIdMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdDeploymentsDeploymentIdMergeRequestsRequestCreation(id, deploymentId, page, perPage, authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername).toEntity(localVarReturnType);
    }

    /**
     * List all merge requests associated with a deployment
     * Lists all merge requests shipped with a specified deployment. Not all deployments can be associated with merge requests.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param authorId Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param reviewerUsername Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;reviewer_id&#x60;.
     * @param labels Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param milestone Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param myReactionEmoji Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param reviewerId Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. &#x60;None&#x60; returns merge requests with no reviewers. &#x60;Any&#x60; returns merge requests with any reviewer. Mutually exclusive with &#x60;reviewer_username&#x60;.
     * @param state Filter merge requests by state. Filtering by &#x60;locked&#x60; usually returns no results, because the &#x60;locked&#x60; state is short-lived.
     * @param orderBy Returns merge requests ordered by &#x60;created_at&#x60;, &#x60;label_priority&#x60;, &#x60;milestone_due&#x60;, &#x60;popularity&#x60;, &#x60;priority&#x60;, &#x60;title&#x60;, &#x60;updated_at&#x60; or &#x60;merged_at&#x60; fields. Introduced in GitLab 14.8. &#x60;merged_at&#x60; [introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/147052) in GitLab 17.2.
     * @param sort Sort results in ascending or descending order.
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param withMergeStatusRecheck If &#x60;true&#x60;, this projection requests (but does not guarantee) an asynchronous recalculation of the &#x60;merge_status&#x60; field. GitLab ignores this attribute when it is requested by users without the Developer, Maintainer, or Owner role.
     * @param createdAfter Return merge requests created on or after the specified time.
     * @param createdBefore Return merge requests created on or before the specified time.
     * @param updatedAfter Return merge requests updated on or after the specified time.
     * @param updatedBefore Return merge requests updated on or before the specified time.
     * @param mergedAfter Return merge requests merged on or after the specified time.
     * @param mergedBefore Return merge requests merged on or before the specified time.
     * @param view If &#x60;simple&#x60;, returns the &#x60;iid&#x60;, URL, title, description, and basic state of merge request.
     * @param scope Return merge requests for the given scope. &#x60;reviews_for_me&#x60; returns merge requests where the current user is assigned as a reviewer. Default is &#x60;all&#x60;.
     * @param sourceBranch Return merge requests with the given source branch.
     * @param sourceProjectId Return merge requests with the given source project ID.
     * @param targetBranch Return merge requests with the given target branch.
     * @param search Search merge requests against their &#x60;title&#x60; and &#x60;description&#x60;. Combine with the &#x60;in&#x60; attribute.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param wip Filter merge requests by their &#x60;wip&#x60; status. &#x60;yes&#x60; returns only draft merge requests, &#x60;no&#x60; returns non-draft merge requests. [Deprecated](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/234098) in GitLab 19.0. Use &#x60;draft&#x60; instead. Mutually exclusive with &#x60;draft&#x60;.
     * @param draft If &#x60;true&#x60;, returns only draft merge requests. If &#x60;false&#x60;, returns only non-draft merge requests. Mutually exclusive with &#x60;wip&#x60;.
     * @param not Return merge requests that do not match the parameters supplied. Accepts: &#x60;labels&#x60;, &#x60;milestone&#x60;, &#x60;author_id&#x60;, &#x60;author_username&#x60;, &#x60;assignee_id&#x60;, &#x60;assignee_username&#x60;, &#x60;reviewer_id&#x60;, &#x60;reviewer_username&#x60;, &#x60;my_reaction_emoji&#x60;.
     * @param notAuthorId &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given user &#x60;id&#x60;. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned merge requests. &#x60;Any&#x60; returns merge requests with an assignee. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests assigned to the given usernames. Instances without a license accept only one username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notReviewerUsername &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given &#x60;username&#x60;. Introduced in GitLab 13.8. Mutually exclusive with &#x60;not[reviewer_id]&#x60;.
     * @param notLabels &#x60;&lt;Negated&gt;&#x60; Returns merge requests matching a comma-separated list of labels. &#x60;None&#x60; lists all merge requests with no labels. &#x60;Any&#x60; lists all merge requests with at least one label. Predefined names are case-insensitive.
     * @param notMilestone &#x60;&lt;Negated&gt;&#x60; Returns merge requests for a specific milestone. &#x60;None&#x60; returns merge requests with no milestone. &#x60;Any&#x60; returns merge requests that have an assigned milestone.
     * @param notMyReactionEmoji &#x60;&lt;Negated&gt;&#x60; Returns merge requests reacted by the authenticated user by the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues not given a reaction. &#x60;Any&#x60; returns issues given at least one reaction.
     * @param notReviewerId &#x60;&lt;Negated&gt;&#x60; Returns merge requests which have the user as a reviewer with the given user &#x60;id&#x60;. Mutually exclusive with &#x60;not[reviewer_username]&#x60;.
     * @param deployedBefore Return merge requests deployed before the specified time.
     * @param deployedAfter Return merge requests deployed after the specified time.
     * @param environment Return merge requests deployed to the given environment.
     * @param mergeUserId Returns merge requests which have been merged by the user with the given user &#x60;id&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_username&#x60;.
     * @param mergeUserUsername Returns merge requests which have been merged by the user with the given &#x60;username&#x60;. [Introduced](https://gitlab.com/gitlab-org/gitlab/-/merge_requests/140002) in GitLab 17.0. Mutually exclusive with &#x60;merge_user_id&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdDeploymentsDeploymentIdMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable String reviewerUsername, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter reviewerId, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable Boolean withMergeStatusRecheck, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable OffsetDateTime mergedAfter, @jakarta.annotation.Nullable OffsetDateTime mergedBefore, @jakarta.annotation.Nullable String view, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String sourceBranch, @jakarta.annotation.Nullable Integer sourceProjectId, @jakarta.annotation.Nullable String targetBranch, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable String wip, @jakarta.annotation.Nullable Boolean draft, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable String notReviewerUsername, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMyReactionEmoji, @jakarta.annotation.Nullable Integer notReviewerId, @jakarta.annotation.Nullable OffsetDateTime deployedBefore, @jakarta.annotation.Nullable OffsetDateTime deployedAfter, @jakarta.annotation.Nullable String environment, @jakarta.annotation.Nullable Integer mergeUserId, @jakarta.annotation.Nullable String mergeUserUsername) throws RestClientResponseException {
        return getApiV4ProjectsIdDeploymentsDeploymentIdMergeRequestsRequestCreation(id, deploymentId, page, perPage, authorId, authorUsername, assigneeId, assigneeUsername, reviewerUsername, labels, milestone, myReactionEmoji, reviewerId, state, orderBy, sort, withLabelsDetails, withMergeStatusRecheck, createdAfter, createdBefore, updatedAfter, updatedBefore, mergedAfter, mergedBefore, view, scope, sourceBranch, sourceProjectId, targetBranch, search, in, wip, draft, not, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notReviewerUsername, notLabels, notMilestone, notMyReactionEmoji, notReviewerId, deployedBefore, deployedAfter, environment, mergeUserId, mergeUserUsername);
    }

    /**
     * List all project deploy keys for a user
     * Lists all project deploy keys accessible to a specified user. Only keys enabled in projects common to the specified user and the authenticated user are listed, where both have at least the Maintainer role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDeployKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UsersUserIdProjectDeployKeysRequestCreation(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'userId' is set
        if (userId == null) {
            throw new RestClientResponseException("Missing the required parameter 'userId' when calling getApiV4UsersUserIdProjectDeployKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("user_id", userId);

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

        ParameterizedTypeReference<List<APIEntitiesDeployKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/users/{user_id}/project_deploy_keys", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all project deploy keys for a user
     * Lists all project deploy keys accessible to a specified user. Only keys enabled in projects common to the specified user and the authenticated user are listed, where both have at least the Maintainer role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesDeployKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesDeployKey> getApiV4UsersUserIdProjectDeployKeys(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdProjectDeployKeysRequestCreation(userId, page, perPage).body(localVarReturnType);
    }

    /**
     * List all project deploy keys for a user
     * Lists all project deploy keys accessible to a specified user. Only keys enabled in projects common to the specified user and the authenticated user are listed, where both have at least the Maintainer role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesDeployKey&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesDeployKey>> getApiV4UsersUserIdProjectDeployKeysWithHttpInfo(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesDeployKey>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UsersUserIdProjectDeployKeysRequestCreation(userId, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all project deploy keys for a user
     * Lists all project deploy keys accessible to a specified user. Only keys enabled in projects common to the specified user and the authenticated user are listed, where both have at least the Maintainer role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param userId ID or username of the user.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UsersUserIdProjectDeployKeysWithResponseSpec(@jakarta.annotation.Nonnull String userId, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4UsersUserIdProjectDeployKeysRequestCreation(userId, page, perPage);
    }

    /**
     * Create a deploy key
     * Creates a deploy key for the GitLab instance. Requires administrator access. This feature was introduced in GitLab 17.5.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param postApiV4DeployKeysRequest The postApiV4DeployKeysRequest parameter
     * @return APIEntitiesDeployKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4DeployKeysRequestCreation(@jakarta.annotation.Nonnull PostApiV4DeployKeysRequest postApiV4DeployKeysRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4DeployKeysRequest' is set
        if (postApiV4DeployKeysRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4DeployKeysRequest' when calling postApiV4DeployKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesDeployKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/deploy_keys", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4DeployKeysRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a deploy key
     * Creates a deploy key for the GitLab instance. Requires administrator access. This feature was introduced in GitLab 17.5.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param postApiV4DeployKeysRequest The postApiV4DeployKeysRequest parameter
     * @return APIEntitiesDeployKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeployKey postApiV4DeployKeys(@jakarta.annotation.Nonnull PostApiV4DeployKeysRequest postApiV4DeployKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4DeployKeysRequestCreation(postApiV4DeployKeysRequest).body(localVarReturnType);
    }

    /**
     * Create a deploy key
     * Creates a deploy key for the GitLab instance. Requires administrator access. This feature was introduced in GitLab 17.5.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param postApiV4DeployKeysRequest The postApiV4DeployKeysRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDeployKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeployKey> postApiV4DeployKeysWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4DeployKeysRequest postApiV4DeployKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4DeployKeysRequestCreation(postApiV4DeployKeysRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a deploy key
     * Creates a deploy key for the GitLab instance. Requires administrator access. This feature was introduced in GitLab 17.5.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param postApiV4DeployKeysRequest The postApiV4DeployKeysRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4DeployKeysWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4DeployKeysRequest postApiV4DeployKeysRequest) throws RestClientResponseException {
        return postApiV4DeployKeysRequestCreation(postApiV4DeployKeysRequest);
    }

    /**
     * Create a group deploy token
     * Creates a group deploy token.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDeployTokensRequest The postApiV4GroupsIdDeployTokensRequest parameter
     * @return APIEntitiesDeployTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdDeployTokensRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDeployTokensRequest postApiV4GroupsIdDeployTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdDeployTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdDeployTokensRequest' is set
        if (postApiV4GroupsIdDeployTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdDeployTokensRequest' when calling postApiV4GroupsIdDeployTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesDeployTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/deploy_tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdDeployTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group deploy token
     * Creates a group deploy token.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDeployTokensRequest The postApiV4GroupsIdDeployTokensRequest parameter
     * @return APIEntitiesDeployTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeployTokenWithToken postApiV4GroupsIdDeployTokens(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDeployTokensRequest postApiV4GroupsIdDeployTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDeployTokensRequestCreation(id, postApiV4GroupsIdDeployTokensRequest).body(localVarReturnType);
    }

    /**
     * Create a group deploy token
     * Creates a group deploy token.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDeployTokensRequest The postApiV4GroupsIdDeployTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDeployTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeployTokenWithToken> postApiV4GroupsIdDeployTokensWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDeployTokensRequest postApiV4GroupsIdDeployTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdDeployTokensRequestCreation(id, postApiV4GroupsIdDeployTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group deploy token
     * Creates a group deploy token.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the group.
     * @param postApiV4GroupsIdDeployTokensRequest The postApiV4GroupsIdDeployTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdDeployTokensWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull PostApiV4GroupsIdDeployTokensRequest postApiV4GroupsIdDeployTokensRequest) throws RestClientResponseException {
        return postApiV4GroupsIdDeployTokensRequestCreation(id, postApiV4GroupsIdDeployTokensRequest);
    }

    /**
     * Add a deploy key for a project
     * Adds a deploy key for a specified project. If the deploy key already exists in another project, it is joined to the current project only if the original one is accessible by the same user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeployKeysRequest The postApiV4ProjectsIdDeployKeysRequest parameter
     * @return APIEntitiesDeployKeysProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdDeployKeysRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeployKeysRequest postApiV4ProjectsIdDeployKeysRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdDeployKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdDeployKeysRequest' is set
        if (postApiV4ProjectsIdDeployKeysRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdDeployKeysRequest' when calling postApiV4ProjectsIdDeployKeys", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesDeployKeysProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_keys", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdDeployKeysRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add a deploy key for a project
     * Adds a deploy key for a specified project. If the deploy key already exists in another project, it is joined to the current project only if the original one is accessible by the same user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeployKeysRequest The postApiV4ProjectsIdDeployKeysRequest parameter
     * @return APIEntitiesDeployKeysProject
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeployKeysProject postApiV4ProjectsIdDeployKeys(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeployKeysRequest postApiV4ProjectsIdDeployKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKeysProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeployKeysRequestCreation(id, postApiV4ProjectsIdDeployKeysRequest).body(localVarReturnType);
    }

    /**
     * Add a deploy key for a project
     * Adds a deploy key for a specified project. If the deploy key already exists in another project, it is joined to the current project only if the original one is accessible by the same user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeployKeysRequest The postApiV4ProjectsIdDeployKeysRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDeployKeysProject&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeployKeysProject> postApiV4ProjectsIdDeployKeysWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeployKeysRequest postApiV4ProjectsIdDeployKeysRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKeysProject> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeployKeysRequestCreation(id, postApiV4ProjectsIdDeployKeysRequest).toEntity(localVarReturnType);
    }

    /**
     * Add a deploy key for a project
     * Adds a deploy key for a specified project. If the deploy key already exists in another project, it is joined to the current project only if the original one is accessible by the same user.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeployKeysRequest The postApiV4ProjectsIdDeployKeysRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdDeployKeysWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeployKeysRequest postApiV4ProjectsIdDeployKeysRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdDeployKeysRequestCreation(id, postApiV4ProjectsIdDeployKeysRequest);
    }

    /**
     * Enable a deploy key
     * Enables a deploy key for a project so this can be used. Returns the enabled key, with a status code 201 when successful.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @return APIEntitiesDeployKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdDeployKeysKeyIdEnableRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdDeployKeysKeyIdEnable", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling postApiV4ProjectsIdDeployKeysKeyIdEnable", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesDeployKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_keys/{key_id}/enable", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Enable a deploy key
     * Enables a deploy key for a project so this can be used. Returns the enabled key, with a status code 201 when successful.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @return APIEntitiesDeployKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeployKey postApiV4ProjectsIdDeployKeysKeyIdEnable(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeployKeysKeyIdEnableRequestCreation(id, keyId).body(localVarReturnType);
    }

    /**
     * Enable a deploy key
     * Enables a deploy key for a project so this can be used. Returns the enabled key, with a status code 201 when successful.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @return ResponseEntity&lt;APIEntitiesDeployKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeployKey> postApiV4ProjectsIdDeployKeysKeyIdEnableWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeployKeysKeyIdEnableRequestCreation(id, keyId).toEntity(localVarReturnType);
    }

    /**
     * Enable a deploy key
     * Enables a deploy key for a project so this can be used. Returns the enabled key, with a status code 201 when successful.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdDeployKeysKeyIdEnableWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId) throws RestClientResponseException {
        return postApiV4ProjectsIdDeployKeysKeyIdEnableRequestCreation(id, keyId);
    }

    /**
     * Create a project deploy token
     * Creates a project deploy token.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeployTokensRequest The postApiV4ProjectsIdDeployTokensRequest parameter
     * @return APIEntitiesDeployTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdDeployTokensRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeployTokensRequest postApiV4ProjectsIdDeployTokensRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdDeployTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdDeployTokensRequest' is set
        if (postApiV4ProjectsIdDeployTokensRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdDeployTokensRequest' when calling postApiV4ProjectsIdDeployTokens", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesDeployTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_tokens", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdDeployTokensRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a project deploy token
     * Creates a project deploy token.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeployTokensRequest The postApiV4ProjectsIdDeployTokensRequest parameter
     * @return APIEntitiesDeployTokenWithToken
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeployTokenWithToken postApiV4ProjectsIdDeployTokens(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeployTokensRequest postApiV4ProjectsIdDeployTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeployTokensRequestCreation(id, postApiV4ProjectsIdDeployTokensRequest).body(localVarReturnType);
    }

    /**
     * Create a project deploy token
     * Creates a project deploy token.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeployTokensRequest The postApiV4ProjectsIdDeployTokensRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDeployTokenWithToken&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeployTokenWithToken> postApiV4ProjectsIdDeployTokensWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeployTokensRequest postApiV4ProjectsIdDeployTokensRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployTokenWithToken> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeployTokensRequestCreation(id, postApiV4ProjectsIdDeployTokensRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a project deploy token
     * Creates a project deploy token.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeployTokensRequest The postApiV4ProjectsIdDeployTokensRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdDeployTokensWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeployTokensRequest postApiV4ProjectsIdDeployTokensRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdDeployTokensRequestCreation(id, postApiV4ProjectsIdDeployTokensRequest);
    }

    /**
     * Create a deployment
     * Creates a deployment. The response includes the &#x60;approvals&#x60; and &#x60;pending_approval_count&#x60; properties.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeploymentsRequest The postApiV4ProjectsIdDeploymentsRequest parameter
     * @return APIEntitiesDeploymentExtended
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdDeploymentsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeploymentsRequest postApiV4ProjectsIdDeploymentsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdDeployments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdDeploymentsRequest' is set
        if (postApiV4ProjectsIdDeploymentsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdDeploymentsRequest' when calling postApiV4ProjectsIdDeployments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesDeploymentExtended> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deployments", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdDeploymentsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a deployment
     * Creates a deployment. The response includes the &#x60;approvals&#x60; and &#x60;pending_approval_count&#x60; properties.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeploymentsRequest The postApiV4ProjectsIdDeploymentsRequest parameter
     * @return APIEntitiesDeploymentExtended
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeploymentExtended postApiV4ProjectsIdDeployments(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeploymentsRequest postApiV4ProjectsIdDeploymentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeploymentExtended> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeploymentsRequestCreation(id, postApiV4ProjectsIdDeploymentsRequest).body(localVarReturnType);
    }

    /**
     * Create a deployment
     * Creates a deployment. The response includes the &#x60;approvals&#x60; and &#x60;pending_approval_count&#x60; properties.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeploymentsRequest The postApiV4ProjectsIdDeploymentsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDeploymentExtended&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeploymentExtended> postApiV4ProjectsIdDeploymentsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeploymentsRequest postApiV4ProjectsIdDeploymentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeploymentExtended> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeploymentsRequestCreation(id, postApiV4ProjectsIdDeploymentsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a deployment
     * Creates a deployment. The response includes the &#x60;approvals&#x60; and &#x60;pending_approval_count&#x60; properties.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdDeploymentsRequest The postApiV4ProjectsIdDeploymentsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdDeploymentsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeploymentsRequest postApiV4ProjectsIdDeploymentsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdDeploymentsRequestCreation(id, postApiV4ProjectsIdDeploymentsRequest);
    }

    /**
     * Approve or reject a deployment
     * Approves or rejects a deployment.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest The postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest parameter
     * @return APIEntitiesDeploymentsApproval
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdDeploymentsDeploymentIdApproval", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'deploymentId' is set
        if (deploymentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'deploymentId' when calling postApiV4ProjectsIdDeploymentsDeploymentIdApproval", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest' is set
        if (postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest' when calling postApiV4ProjectsIdDeploymentsDeploymentIdApproval", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("deployment_id", deploymentId);

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

        ParameterizedTypeReference<APIEntitiesDeploymentsApproval> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deployments/{deployment_id}/approval", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Approve or reject a deployment
     * Approves or rejects a deployment.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest The postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest parameter
     * @return APIEntitiesDeploymentsApproval
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeploymentsApproval postApiV4ProjectsIdDeploymentsDeploymentIdApproval(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeploymentsApproval> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequestCreation(id, deploymentId, postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest).body(localVarReturnType);
    }

    /**
     * Approve or reject a deployment
     * Approves or rejects a deployment.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest The postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDeploymentsApproval&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeploymentsApproval> postApiV4ProjectsIdDeploymentsDeploymentIdApprovalWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeploymentsApproval> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequestCreation(id, deploymentId, postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest).toEntity(localVarReturnType);
    }

    /**
     * Approve or reject a deployment
     * Approves or rejects a deployment.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest The postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdDeploymentsDeploymentIdApprovalWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nonnull PostApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequestCreation(id, deploymentId, postApiV4ProjectsIdDeploymentsDeploymentIdApprovalRequest);
    }

    /**
     * Update a deploy key
     * Updates a deploy key for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @param putApiV4ProjectsIdDeployKeysKeyIdRequest The putApiV4ProjectsIdDeployKeysKeyIdRequest parameter
     * @return APIEntitiesDeployKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdDeployKeysKeyIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId, @jakarta.annotation.Nullable PutApiV4ProjectsIdDeployKeysKeyIdRequest putApiV4ProjectsIdDeployKeysKeyIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdDeployKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new RestClientResponseException("Missing the required parameter 'keyId' when calling putApiV4ProjectsIdDeployKeysKeyId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("key_id", keyId);

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

        ParameterizedTypeReference<APIEntitiesDeployKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deploy_keys/{key_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdDeployKeysKeyIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a deploy key
     * Updates a deploy key for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @param putApiV4ProjectsIdDeployKeysKeyIdRequest The putApiV4ProjectsIdDeployKeysKeyIdRequest parameter
     * @return APIEntitiesDeployKey
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeployKey putApiV4ProjectsIdDeployKeysKeyId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId, @jakarta.annotation.Nullable PutApiV4ProjectsIdDeployKeysKeyIdRequest putApiV4ProjectsIdDeployKeysKeyIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdDeployKeysKeyIdRequestCreation(id, keyId, putApiV4ProjectsIdDeployKeysKeyIdRequest).body(localVarReturnType);
    }

    /**
     * Update a deploy key
     * Updates a deploy key for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @param putApiV4ProjectsIdDeployKeysKeyIdRequest The putApiV4ProjectsIdDeployKeysKeyIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDeployKey&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeployKey> putApiV4ProjectsIdDeployKeysKeyIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId, @jakarta.annotation.Nullable PutApiV4ProjectsIdDeployKeysKeyIdRequest putApiV4ProjectsIdDeployKeysKeyIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeployKey> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdDeployKeysKeyIdRequestCreation(id, keyId, putApiV4ProjectsIdDeployKeysKeyIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a deploy key
     * Updates a deploy key for a project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param keyId ID of the deploy key.
     * @param putApiV4ProjectsIdDeployKeysKeyIdRequest The putApiV4ProjectsIdDeployKeysKeyIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdDeployKeysKeyIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer keyId, @jakarta.annotation.Nullable PutApiV4ProjectsIdDeployKeysKeyIdRequest putApiV4ProjectsIdDeployKeysKeyIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdDeployKeysKeyIdRequestCreation(id, keyId, putApiV4ProjectsIdDeployKeysKeyIdRequest);
    }

    /**
     * Update a deployment
     * Updates a specified deployment. The response includes the &#x60;approvals&#x60; and &#x60;pending_approval_count&#x60; properties.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param putApiV4ProjectsIdDeploymentsDeploymentIdRequest The putApiV4ProjectsIdDeploymentsDeploymentIdRequest parameter
     * @return APIEntitiesDeploymentExtended
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdDeploymentsDeploymentIdRequest putApiV4ProjectsIdDeploymentsDeploymentIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdDeploymentsDeploymentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'deploymentId' is set
        if (deploymentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'deploymentId' when calling putApiV4ProjectsIdDeploymentsDeploymentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdDeploymentsDeploymentIdRequest' is set
        if (putApiV4ProjectsIdDeploymentsDeploymentIdRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdDeploymentsDeploymentIdRequest' when calling putApiV4ProjectsIdDeploymentsDeploymentId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("deployment_id", deploymentId);

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

        ParameterizedTypeReference<APIEntitiesDeploymentExtended> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/deployments/{deployment_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdDeploymentsDeploymentIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a deployment
     * Updates a specified deployment. The response includes the &#x60;approvals&#x60; and &#x60;pending_approval_count&#x60; properties.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param putApiV4ProjectsIdDeploymentsDeploymentIdRequest The putApiV4ProjectsIdDeploymentsDeploymentIdRequest parameter
     * @return APIEntitiesDeploymentExtended
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesDeploymentExtended putApiV4ProjectsIdDeploymentsDeploymentId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdDeploymentsDeploymentIdRequest putApiV4ProjectsIdDeploymentsDeploymentIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeploymentExtended> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(id, deploymentId, putApiV4ProjectsIdDeploymentsDeploymentIdRequest).body(localVarReturnType);
    }

    /**
     * Update a deployment
     * Updates a specified deployment. The response includes the &#x60;approvals&#x60; and &#x60;pending_approval_count&#x60; properties.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param putApiV4ProjectsIdDeploymentsDeploymentIdRequest The putApiV4ProjectsIdDeploymentsDeploymentIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesDeploymentExtended&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesDeploymentExtended> putApiV4ProjectsIdDeploymentsDeploymentIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdDeploymentsDeploymentIdRequest putApiV4ProjectsIdDeploymentsDeploymentIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesDeploymentExtended> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(id, deploymentId, putApiV4ProjectsIdDeploymentsDeploymentIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a deployment
     * Updates a specified deployment. The response includes the &#x60;approvals&#x60; and &#x60;pending_approval_count&#x60; properties.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param deploymentId ID of the deployment.
     * @param putApiV4ProjectsIdDeploymentsDeploymentIdRequest The putApiV4ProjectsIdDeploymentsDeploymentIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdDeploymentsDeploymentIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer deploymentId, @jakarta.annotation.Nonnull PutApiV4ProjectsIdDeploymentsDeploymentIdRequest putApiV4ProjectsIdDeploymentsDeploymentIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdDeploymentsDeploymentIdRequestCreation(id, deploymentId, putApiV4ProjectsIdDeploymentsDeploymentIdRequest);
    }
}
