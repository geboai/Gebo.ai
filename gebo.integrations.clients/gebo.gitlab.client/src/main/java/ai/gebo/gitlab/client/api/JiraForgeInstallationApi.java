package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBasicSuccess;
import ai.gebo.gitlab.client.model.PutApiV4IntegrationsJiraForgeInstallationRequest;

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
public class JiraForgeInstallationApi {
    private ApiClient apiClient;

    public JiraForgeInstallationApi() {
        this(new ApiClient());
    }

    public JiraForgeInstallationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Destroy the GitLab for Jira (Forge) installation on app uninstall
     * Called by the Forge app preUninstall hook. A pure-native install fires no Connect uninstalled event, so this is what removes the installation, its subscriptions, and deactivates the linked jira_cloud_app integrations. FIT authentication carries the same trust as the signed Connect lifecycle event, so no Jira admin check is needed. Succeeds with no change when the installation is already gone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4IntegrationsJiraForgeInstallationRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/integrations/jira_forge/installation", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Destroy the GitLab for Jira (Forge) installation on app uninstall
     * Called by the Forge app preUninstall hook. A pure-native install fires no Connect uninstalled event, so this is what removes the installation, its subscriptions, and deactivates the linked jira_cloud_app integrations. FIT authentication carries the same trust as the signed Connect lifecycle event, so no Jira admin check is needed. Succeeds with no change when the installation is already gone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicSuccess deleteApiV4IntegrationsJiraForgeInstallation() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4IntegrationsJiraForgeInstallationRequestCreation().body(localVarReturnType);
    }

    /**
     * Destroy the GitLab for Jira (Forge) installation on app uninstall
     * Called by the Forge app preUninstall hook. A pure-native install fires no Connect uninstalled event, so this is what removes the installation, its subscriptions, and deactivates the linked jira_cloud_app integrations. FIT authentication carries the same trust as the signed Connect lifecycle event, so no Jira admin check is needed. Succeeds with no change when the installation is already gone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * @return ResponseEntity&lt;APIEntitiesBasicSuccess&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicSuccess> deleteApiV4IntegrationsJiraForgeInstallationWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4IntegrationsJiraForgeInstallationRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Destroy the GitLab for Jira (Forge) installation on app uninstall
     * Called by the Forge app preUninstall hook. A pure-native install fires no Connect uninstalled event, so this is what removes the installation, its subscriptions, and deactivates the linked jira_cloud_app integrations. FIT authentication carries the same trust as the signed Connect lifecycle event, so no Jira admin check is needed. Succeeds with no change when the installation is already gone.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4IntegrationsJiraForgeInstallationWithResponseSpec() throws RestClientResponseException {
        return deleteApiV4IntegrationsJiraForgeInstallationRequestCreation();
    }

    /**
     * Register the GitLab for Jira (Forge) system token for direct dev-info sync
     * Stores the Forge app system OAuth token (X-Forge-Oauth-System header) and the Jira apiBaseUrl (from the FIT), so GitLab pushes dev-info directly to Jira. See Atlassian::Forge::SystemTokenClient.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4IntegrationsJiraForgeInstallationForgeTokenRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/integrations/jira_forge/installation/forge_token", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Register the GitLab for Jira (Forge) system token for direct dev-info sync
     * Stores the Forge app system OAuth token (X-Forge-Oauth-System header) and the Jira apiBaseUrl (from the FIT), so GitLab pushes dev-info directly to Jira. See Atlassian::Forge::SystemTokenClient.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicSuccess postApiV4IntegrationsJiraForgeInstallationForgeToken() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsJiraForgeInstallationForgeTokenRequestCreation().body(localVarReturnType);
    }

    /**
     * Register the GitLab for Jira (Forge) system token for direct dev-info sync
     * Stores the Forge app system OAuth token (X-Forge-Oauth-System header) and the Jira apiBaseUrl (from the FIT), so GitLab pushes dev-info directly to Jira. See Atlassian::Forge::SystemTokenClient.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @return ResponseEntity&lt;APIEntitiesBasicSuccess&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicSuccess> postApiV4IntegrationsJiraForgeInstallationForgeTokenWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsJiraForgeInstallationForgeTokenRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Register the GitLab for Jira (Forge) system token for direct dev-info sync
     * Stores the Forge app system OAuth token (X-Forge-Oauth-System header) and the Jira apiBaseUrl (from the FIT), so GitLab pushes dev-info directly to Jira. See Atlassian::Forge::SystemTokenClient.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4IntegrationsJiraForgeInstallationForgeTokenWithResponseSpec() throws RestClientResponseException {
        return postApiV4IntegrationsJiraForgeInstallationForgeTokenRequestCreation();
    }

    /**
     * Update the GitLab for Jira (Forge) installation instance URL
     * Sets the GitLab instance the installation points at. Omit instance_url (or send null) for GitLab.com. Requires a Jira site or organization admin.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param putApiV4IntegrationsJiraForgeInstallationRequest The putApiV4IntegrationsJiraForgeInstallationRequest parameter
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4IntegrationsJiraForgeInstallationRequestCreation(@jakarta.annotation.Nullable PutApiV4IntegrationsJiraForgeInstallationRequest putApiV4IntegrationsJiraForgeInstallationRequest) throws RestClientResponseException {
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
        return apiClient.invokeAPI("/api/v4/integrations/jira_forge/installation", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4IntegrationsJiraForgeInstallationRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update the GitLab for Jira (Forge) installation instance URL
     * Sets the GitLab instance the installation points at. Omit instance_url (or send null) for GitLab.com. Requires a Jira site or organization admin.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param putApiV4IntegrationsJiraForgeInstallationRequest The putApiV4IntegrationsJiraForgeInstallationRequest parameter
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicSuccess putApiV4IntegrationsJiraForgeInstallation(@jakarta.annotation.Nullable PutApiV4IntegrationsJiraForgeInstallationRequest putApiV4IntegrationsJiraForgeInstallationRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4IntegrationsJiraForgeInstallationRequestCreation(putApiV4IntegrationsJiraForgeInstallationRequest).body(localVarReturnType);
    }

    /**
     * Update the GitLab for Jira (Forge) installation instance URL
     * Sets the GitLab instance the installation points at. Omit instance_url (or send null) for GitLab.com. Requires a Jira site or organization admin.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param putApiV4IntegrationsJiraForgeInstallationRequest The putApiV4IntegrationsJiraForgeInstallationRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBasicSuccess&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicSuccess> putApiV4IntegrationsJiraForgeInstallationWithHttpInfo(@jakarta.annotation.Nullable PutApiV4IntegrationsJiraForgeInstallationRequest putApiV4IntegrationsJiraForgeInstallationRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4IntegrationsJiraForgeInstallationRequestCreation(putApiV4IntegrationsJiraForgeInstallationRequest).toEntity(localVarReturnType);
    }

    /**
     * Update the GitLab for Jira (Forge) installation instance URL
     * Sets the GitLab instance the installation points at. Omit instance_url (or send null) for GitLab.com. Requires a Jira site or organization admin.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param putApiV4IntegrationsJiraForgeInstallationRequest The putApiV4IntegrationsJiraForgeInstallationRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4IntegrationsJiraForgeInstallationWithResponseSpec(@jakarta.annotation.Nullable PutApiV4IntegrationsJiraForgeInstallationRequest putApiV4IntegrationsJiraForgeInstallationRequest) throws RestClientResponseException {
        return putApiV4IntegrationsJiraForgeInstallationRequestCreation(putApiV4IntegrationsJiraForgeInstallationRequest);
    }
}
