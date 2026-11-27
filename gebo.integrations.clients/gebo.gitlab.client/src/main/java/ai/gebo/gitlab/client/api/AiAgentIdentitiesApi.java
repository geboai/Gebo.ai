package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesAiExternalAgentsIdentity;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdAiAgentIdentitiesRequest;

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
public class AiAgentIdentitiesApi {
    private ApiClient apiClient;

    public AiAgentIdentitiesApi() {
        this(new ApiClient());
    }

    public AiAgentIdentitiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Register an external agent identity.
     * Registers a new agent identity for the current user, project, agent type, and machine fingerprint combination. Returns an existing record if one already exists. Returns 403 if the identity has been revoked.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentIdentitiesRequest The postApiV4ProjectsIdAiAgentIdentitiesRequest parameter
     * @return APIEntitiesAiExternalAgentsIdentity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdAiAgentIdentitiesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentIdentitiesRequest postApiV4ProjectsIdAiAgentIdentitiesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdAiAgentIdentities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdAiAgentIdentitiesRequest' is set
        if (postApiV4ProjectsIdAiAgentIdentitiesRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdAiAgentIdentitiesRequest' when calling postApiV4ProjectsIdAiAgentIdentities", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesAiExternalAgentsIdentity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/ai_agent/identities", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdAiAgentIdentitiesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Register an external agent identity.
     * Registers a new agent identity for the current user, project, agent type, and machine fingerprint combination. Returns an existing record if one already exists. Returns 403 if the identity has been revoked.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentIdentitiesRequest The postApiV4ProjectsIdAiAgentIdentitiesRequest parameter
     * @return APIEntitiesAiExternalAgentsIdentity
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesAiExternalAgentsIdentity postApiV4ProjectsIdAiAgentIdentities(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentIdentitiesRequest postApiV4ProjectsIdAiAgentIdentitiesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiExternalAgentsIdentity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAiAgentIdentitiesRequestCreation(id, postApiV4ProjectsIdAiAgentIdentitiesRequest).body(localVarReturnType);
    }

    /**
     * Register an external agent identity.
     * Registers a new agent identity for the current user, project, agent type, and machine fingerprint combination. Returns an existing record if one already exists. Returns 403 if the identity has been revoked.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentIdentitiesRequest The postApiV4ProjectsIdAiAgentIdentitiesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesAiExternalAgentsIdentity&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesAiExternalAgentsIdentity> postApiV4ProjectsIdAiAgentIdentitiesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentIdentitiesRequest postApiV4ProjectsIdAiAgentIdentitiesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesAiExternalAgentsIdentity> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAiAgentIdentitiesRequestCreation(id, postApiV4ProjectsIdAiAgentIdentitiesRequest).toEntity(localVarReturnType);
    }

    /**
     * Register an external agent identity.
     * Registers a new agent identity for the current user, project, agent type, and machine fingerprint combination. Returns an existing record if one already exists. Returns 403 if the identity has been revoked.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdAiAgentIdentitiesRequest The postApiV4ProjectsIdAiAgentIdentitiesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdAiAgentIdentitiesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdAiAgentIdentitiesRequest postApiV4ProjectsIdAiAgentIdentitiesRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdAiAgentIdentitiesRequestCreation(id, postApiV4ProjectsIdAiAgentIdentitiesRequest);
    }
}
