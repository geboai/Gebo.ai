package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBasicSuccess;
import ai.gebo.gitlab.client.model.PostApiV4IntegrationsJiraForgeUserDelegationRequest;

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
public class JiraForgeUserDelegationApi {
    private ApiClient apiClient;

    public JiraForgeUserDelegationApi() {
        this(new ApiClient());
    }

    public JiraForgeUserDelegationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create a GitLab for Jira (Forge) user delegation token
     * Mints a short-lived GitLab-signed token identifying the signed-in GitLab user, scoped to the Jira account and site it is minted for. Accepts only the OAuth token issued to the Forge app at sign-in. The Forge app stores it at config-page sign-in and presents it on the FIT-authenticated subscribe call, which cannot carry the user OAuth token (Forge attaches the FIT only to app-context calls); the scope claims must match that call&#39;s FIT.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4IntegrationsJiraForgeUserDelegationRequest The postApiV4IntegrationsJiraForgeUserDelegationRequest parameter
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4IntegrationsJiraForgeUserDelegationRequestCreation(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraForgeUserDelegationRequest postApiV4IntegrationsJiraForgeUserDelegationRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4IntegrationsJiraForgeUserDelegationRequest' is set
        if (postApiV4IntegrationsJiraForgeUserDelegationRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4IntegrationsJiraForgeUserDelegationRequest' when calling postApiV4IntegrationsJiraForgeUserDelegation", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/integrations/jira_forge/user_delegation", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4IntegrationsJiraForgeUserDelegationRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a GitLab for Jira (Forge) user delegation token
     * Mints a short-lived GitLab-signed token identifying the signed-in GitLab user, scoped to the Jira account and site it is minted for. Accepts only the OAuth token issued to the Forge app at sign-in. The Forge app stores it at config-page sign-in and presents it on the FIT-authenticated subscribe call, which cannot carry the user OAuth token (Forge attaches the FIT only to app-context calls); the scope claims must match that call&#39;s FIT.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4IntegrationsJiraForgeUserDelegationRequest The postApiV4IntegrationsJiraForgeUserDelegationRequest parameter
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicSuccess postApiV4IntegrationsJiraForgeUserDelegation(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraForgeUserDelegationRequest postApiV4IntegrationsJiraForgeUserDelegationRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsJiraForgeUserDelegationRequestCreation(postApiV4IntegrationsJiraForgeUserDelegationRequest).body(localVarReturnType);
    }

    /**
     * Create a GitLab for Jira (Forge) user delegation token
     * Mints a short-lived GitLab-signed token identifying the signed-in GitLab user, scoped to the Jira account and site it is minted for. Accepts only the OAuth token issued to the Forge app at sign-in. The Forge app stores it at config-page sign-in and presents it on the FIT-authenticated subscribe call, which cannot carry the user OAuth token (Forge attaches the FIT only to app-context calls); the scope claims must match that call&#39;s FIT.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4IntegrationsJiraForgeUserDelegationRequest The postApiV4IntegrationsJiraForgeUserDelegationRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBasicSuccess&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicSuccess> postApiV4IntegrationsJiraForgeUserDelegationWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraForgeUserDelegationRequest postApiV4IntegrationsJiraForgeUserDelegationRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsJiraForgeUserDelegationRequestCreation(postApiV4IntegrationsJiraForgeUserDelegationRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a GitLab for Jira (Forge) user delegation token
     * Mints a short-lived GitLab-signed token identifying the signed-in GitLab user, scoped to the Jira account and site it is minted for. Accepts only the OAuth token issued to the Forge app at sign-in. The Forge app stores it at config-page sign-in and presents it on the FIT-authenticated subscribe call, which cannot carry the user OAuth token (Forge attaches the FIT only to app-context calls); the scope claims must match that call&#39;s FIT.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad Request
     * @param postApiV4IntegrationsJiraForgeUserDelegationRequest The postApiV4IntegrationsJiraForgeUserDelegationRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4IntegrationsJiraForgeUserDelegationWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraForgeUserDelegationRequest postApiV4IntegrationsJiraForgeUserDelegationRequest) throws RestClientResponseException {
        return postApiV4IntegrationsJiraForgeUserDelegationRequestCreation(postApiV4IntegrationsJiraForgeUserDelegationRequest);
    }
}
