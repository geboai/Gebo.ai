package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;

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
public class ProjectGoogleCloudIntegrationApi {
    private ApiClient apiClient;

    public ProjectGoogleCloudIntegrationApi() {
        this(new ApiClient());
    }

    public ProjectGoogleCloudIntegrationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve setup script for Google Cloud integration
     * Retrieves the setup script for Google Cloud integration. You must have the Maintainer or Owner role for the project. Only the Google Artifact Management integration is supported. The script creates IAM policies to access Google Artifact Registry. The Artifact Registry Reader role is granted to members with at least the Reporter role. The Artifact Registry Writer role is granted to members with at least the Developer role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param enableGoogleCloudArtifactRegistry If &#x60;true&#x60;, indicates that the Google Artifact Management integration should be enabled.
     * @param googleCloudArtifactRegistryProjectId Project ID for the Google Cloud Artifact Registry.
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdGoogleCloudSetupIntegrationsShRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean enableGoogleCloudArtifactRegistry, @jakarta.annotation.Nullable String googleCloudArtifactRegistryProjectId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdGoogleCloudSetupIntegrationsSh", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "enable_google_cloud_artifact_registry", enableGoogleCloudArtifactRegistry));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "google_cloud_artifact_registry_project_id", googleCloudArtifactRegistryProjectId));

        final String[] localVarAccepts = { 
            "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/google_cloud/setup/integrations.sh", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve setup script for Google Cloud integration
     * Retrieves the setup script for Google Cloud integration. You must have the Maintainer or Owner role for the project. Only the Google Artifact Management integration is supported. The script creates IAM policies to access Google Artifact Registry. The Artifact Registry Reader role is granted to members with at least the Reporter role. The Artifact Registry Writer role is granted to members with at least the Developer role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param enableGoogleCloudArtifactRegistry If &#x60;true&#x60;, indicates that the Google Artifact Management integration should be enabled.
     * @param googleCloudArtifactRegistryProjectId Project ID for the Google Cloud Artifact Registry.
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public String getApiV4ProjectsIdGoogleCloudSetupIntegrationsSh(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean enableGoogleCloudArtifactRegistry, @jakarta.annotation.Nullable String googleCloudArtifactRegistryProjectId) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdGoogleCloudSetupIntegrationsShRequestCreation(id, enableGoogleCloudArtifactRegistry, googleCloudArtifactRegistryProjectId).body(localVarReturnType);
    }

    /**
     * Retrieve setup script for Google Cloud integration
     * Retrieves the setup script for Google Cloud integration. You must have the Maintainer or Owner role for the project. Only the Google Artifact Management integration is supported. The script creates IAM policies to access Google Artifact Registry. The Artifact Registry Reader role is granted to members with at least the Reporter role. The Artifact Registry Writer role is granted to members with at least the Developer role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param enableGoogleCloudArtifactRegistry If &#x60;true&#x60;, indicates that the Google Artifact Management integration should be enabled.
     * @param googleCloudArtifactRegistryProjectId Project ID for the Google Cloud Artifact Registry.
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> getApiV4ProjectsIdGoogleCloudSetupIntegrationsShWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean enableGoogleCloudArtifactRegistry, @jakarta.annotation.Nullable String googleCloudArtifactRegistryProjectId) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdGoogleCloudSetupIntegrationsShRequestCreation(id, enableGoogleCloudArtifactRegistry, googleCloudArtifactRegistryProjectId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve setup script for Google Cloud integration
     * Retrieves the setup script for Google Cloud integration. You must have the Maintainer or Owner role for the project. Only the Google Artifact Management integration is supported. The script creates IAM policies to access Google Artifact Registry. The Artifact Registry Reader role is granted to members with at least the Reporter role. The Artifact Registry Writer role is granted to members with at least the Developer role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param enableGoogleCloudArtifactRegistry If &#x60;true&#x60;, indicates that the Google Artifact Management integration should be enabled.
     * @param googleCloudArtifactRegistryProjectId Project ID for the Google Cloud Artifact Registry.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdGoogleCloudSetupIntegrationsShWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Boolean enableGoogleCloudArtifactRegistry, @jakarta.annotation.Nullable String googleCloudArtifactRegistryProjectId) throws RestClientResponseException {
        return getApiV4ProjectsIdGoogleCloudSetupIntegrationsShRequestCreation(id, enableGoogleCloudArtifactRegistry, googleCloudArtifactRegistryProjectId);
    }

    /**
     * Retrieve configuration script for Google Cloud runner provisioning
     * Retrieves the configuration script for Google Cloud runner provisioning. You must have the Maintainer or Owner role for the project. The script enables required services in the specified Google Cloud project, and creates a &#x60;GRITProvisioner&#x60; role and a &#x60;grit-provisioner&#x60; service account.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param googleCloudProjectId ID of the Google Cloud project.
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdGoogleCloudSetupRunnerDeploymentProjectShRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String googleCloudProjectId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdGoogleCloudSetupRunnerDeploymentProjectSh", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'googleCloudProjectId' is set
        if (googleCloudProjectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'googleCloudProjectId' when calling getApiV4ProjectsIdGoogleCloudSetupRunnerDeploymentProjectSh", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "google_cloud_project_id", googleCloudProjectId));

        final String[] localVarAccepts = { 
            "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/google_cloud/setup/runner_deployment_project.sh", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve configuration script for Google Cloud runner provisioning
     * Retrieves the configuration script for Google Cloud runner provisioning. You must have the Maintainer or Owner role for the project. The script enables required services in the specified Google Cloud project, and creates a &#x60;GRITProvisioner&#x60; role and a &#x60;grit-provisioner&#x60; service account.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param googleCloudProjectId ID of the Google Cloud project.
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public String getApiV4ProjectsIdGoogleCloudSetupRunnerDeploymentProjectSh(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String googleCloudProjectId) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdGoogleCloudSetupRunnerDeploymentProjectShRequestCreation(id, googleCloudProjectId).body(localVarReturnType);
    }

    /**
     * Retrieve configuration script for Google Cloud runner provisioning
     * Retrieves the configuration script for Google Cloud runner provisioning. You must have the Maintainer or Owner role for the project. The script enables required services in the specified Google Cloud project, and creates a &#x60;GRITProvisioner&#x60; role and a &#x60;grit-provisioner&#x60; service account.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param googleCloudProjectId ID of the Google Cloud project.
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> getApiV4ProjectsIdGoogleCloudSetupRunnerDeploymentProjectShWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String googleCloudProjectId) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdGoogleCloudSetupRunnerDeploymentProjectShRequestCreation(id, googleCloudProjectId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve configuration script for Google Cloud runner provisioning
     * Retrieves the configuration script for Google Cloud runner provisioning. You must have the Maintainer or Owner role for the project. The script enables required services in the specified Google Cloud project, and creates a &#x60;GRITProvisioner&#x60; role and a &#x60;grit-provisioner&#x60; service account.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param googleCloudProjectId ID of the Google Cloud project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdGoogleCloudSetupRunnerDeploymentProjectShWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable String googleCloudProjectId) throws RestClientResponseException {
        return getApiV4ProjectsIdGoogleCloudSetupRunnerDeploymentProjectShRequestCreation(id, googleCloudProjectId);
    }
}
