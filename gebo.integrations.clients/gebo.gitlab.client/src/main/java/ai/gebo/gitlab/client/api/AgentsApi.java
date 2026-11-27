package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiAllowedAgents;

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
public class AgentsApi {
    private ApiClient apiClient;

    public AgentsApi() {
        this(new ApiClient());
    }

    public AgentsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all GitLab agents for Kubernetes by job token
     * Retrieves the job that generated the specified &#x60;CI_JOB_TOKEN&#x60;, along with a list of the GitLab agents for Kubernetes the job is allowed to access.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @return APIEntitiesCiAllowedAgents
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4JobAllowedAgentsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesCiAllowedAgents> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/job/allowed_agents", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all GitLab agents for Kubernetes by job token
     * Retrieves the job that generated the specified &#x60;CI_JOB_TOKEN&#x60;, along with a list of the GitLab agents for Kubernetes the job is allowed to access.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @return APIEntitiesCiAllowedAgents
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiAllowedAgents getApiV4JobAllowedAgents() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiAllowedAgents> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4JobAllowedAgentsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all GitLab agents for Kubernetes by job token
     * Retrieves the job that generated the specified &#x60;CI_JOB_TOKEN&#x60;, along with a list of the GitLab agents for Kubernetes the job is allowed to access.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @return ResponseEntity&lt;APIEntitiesCiAllowedAgents&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiAllowedAgents> getApiV4JobAllowedAgentsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiAllowedAgents> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4JobAllowedAgentsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all GitLab agents for Kubernetes by job token
     * Retrieves the job that generated the specified &#x60;CI_JOB_TOKEN&#x60;, along with a list of the GitLab agents for Kubernetes the job is allowed to access.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4JobAllowedAgentsWithResponseSpec() throws RestClientResponseException {
        return getApiV4JobAllowedAgentsRequestCreation();
    }
}
