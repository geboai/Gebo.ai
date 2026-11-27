package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBasicSuccess;
import ai.gebo.gitlab.client.model.PostApiV4IntegrationsJiraConnectSubscriptionsRequest;

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
public class JiraConnectSubscriptionsApi {
    private ApiClient apiClient;

    public JiraConnectSubscriptionsApi() {
        this(new ApiClient());
    }

    public JiraConnectSubscriptionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Subscribe a namespace to a JiraConnectInstallation
     * Subscribes the namespace to the JiraConnectInstallation
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4IntegrationsJiraConnectSubscriptionsRequest The postApiV4IntegrationsJiraConnectSubscriptionsRequest parameter
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4IntegrationsJiraConnectSubscriptionsRequestCreation(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraConnectSubscriptionsRequest postApiV4IntegrationsJiraConnectSubscriptionsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4IntegrationsJiraConnectSubscriptionsRequest' is set
        if (postApiV4IntegrationsJiraConnectSubscriptionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4IntegrationsJiraConnectSubscriptionsRequest' when calling postApiV4IntegrationsJiraConnectSubscriptions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/integrations/jira_connect/subscriptions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4IntegrationsJiraConnectSubscriptionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Subscribe a namespace to a JiraConnectInstallation
     * Subscribes the namespace to the JiraConnectInstallation
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4IntegrationsJiraConnectSubscriptionsRequest The postApiV4IntegrationsJiraConnectSubscriptionsRequest parameter
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicSuccess postApiV4IntegrationsJiraConnectSubscriptions(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraConnectSubscriptionsRequest postApiV4IntegrationsJiraConnectSubscriptionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsJiraConnectSubscriptionsRequestCreation(postApiV4IntegrationsJiraConnectSubscriptionsRequest).body(localVarReturnType);
    }

    /**
     * Subscribe a namespace to a JiraConnectInstallation
     * Subscribes the namespace to the JiraConnectInstallation
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4IntegrationsJiraConnectSubscriptionsRequest The postApiV4IntegrationsJiraConnectSubscriptionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBasicSuccess&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicSuccess> postApiV4IntegrationsJiraConnectSubscriptionsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraConnectSubscriptionsRequest postApiV4IntegrationsJiraConnectSubscriptionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsJiraConnectSubscriptionsRequestCreation(postApiV4IntegrationsJiraConnectSubscriptionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Subscribe a namespace to a JiraConnectInstallation
     * Subscribes the namespace to the JiraConnectInstallation
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4IntegrationsJiraConnectSubscriptionsRequest The postApiV4IntegrationsJiraConnectSubscriptionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4IntegrationsJiraConnectSubscriptionsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraConnectSubscriptionsRequest postApiV4IntegrationsJiraConnectSubscriptionsRequest) throws RestClientResponseException {
        return postApiV4IntegrationsJiraConnectSubscriptionsRequestCreation(postApiV4IntegrationsJiraConnectSubscriptionsRequest);
    }
}
