package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ConfigurationModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.ProjectConfigurationModel;

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
public class ConfigurationApi {
    private ApiClient apiClient;

    public ConfigurationApi() {
        this(new ApiClient());
    }

    public ConfigurationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get instance configuration
     * Returns an object containing information about the global configuration.
     * <p><b>200</b> - OK
     * @return ConfigurationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getConfigurationRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<ConfigurationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/configuration", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get instance configuration
     * Returns an object containing information about the global configuration.
     * <p><b>200</b> - OK
     * @return ConfigurationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ConfigurationModel getConfiguration() throws RestClientResponseException {
        ParameterizedTypeReference<ConfigurationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getConfigurationRequestCreation().body(localVarReturnType);
    }

    /**
     * Get instance configuration
     * Returns an object containing information about the global configuration.
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;ConfigurationModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ConfigurationModel> getConfigurationWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<ConfigurationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getConfigurationRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get instance configuration
     * Returns an object containing information about the global configuration.
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getConfigurationWithResponseSpec() throws RestClientResponseException {
        return getConfigurationRequestCreation();
    }

    /**
     * View project configuration
     * Returns the configuration scoped to a specific project, including all global configuration properties plus project-specific settings.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the user cannot view it.
     * @param id Project id
     * @return ProjectConfigurationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProjectConfigurationRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getProjectConfiguration", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<ProjectConfigurationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/configuration", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View project configuration
     * Returns the configuration scoped to a specific project, including all global configuration properties plus project-specific settings.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the user cannot view it.
     * @param id Project id
     * @return ProjectConfigurationModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectConfigurationModel getProjectConfiguration(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectConfigurationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectConfigurationRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View project configuration
     * Returns the configuration scoped to a specific project, including all global configuration properties plus project-specific settings.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the user cannot view it.
     * @param id Project id
     * @return ResponseEntity&lt;ProjectConfigurationModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectConfigurationModel> getProjectConfigurationWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectConfigurationModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectConfigurationRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View project configuration
     * Returns the configuration scoped to a specific project, including all global configuration properties plus project-specific settings.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project does not exist or the user cannot view it.
     * @param id Project id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getProjectConfigurationWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getProjectConfigurationRequestCreation(id);
    }
}
