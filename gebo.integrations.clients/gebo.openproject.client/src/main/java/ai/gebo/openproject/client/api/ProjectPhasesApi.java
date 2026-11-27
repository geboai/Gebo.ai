package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.ProjectPhaseModel;

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
public class ProjectPhasesApi {
    private ApiClient apiClient;

    public ProjectPhasesApi() {
        this(new ApiClient());
    }

    public ProjectPhasesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get a project phase
     * Gets a project phase resource. This resource contains an instance of a ProjectPhaseDefinition within a project which can then have project specific dates.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project phase does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project phase
     * @param id Project phase id
     * @return ProjectPhaseModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProjectPhaseRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getProjectPhase", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProjectPhaseModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/project_phases/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a project phase
     * Gets a project phase resource. This resource contains an instance of a ProjectPhaseDefinition within a project which can then have project specific dates.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project phase does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project phase
     * @param id Project phase id
     * @return ProjectPhaseModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectPhaseModel getProjectPhase(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectPhaseModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectPhaseRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a project phase
     * Gets a project phase resource. This resource contains an instance of a ProjectPhaseDefinition within a project which can then have project specific dates.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project phase does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project phase
     * @param id Project phase id
     * @return ResponseEntity&lt;ProjectPhaseModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectPhaseModel> getProjectPhaseWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectPhaseModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectPhaseRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a project phase
     * Gets a project phase resource. This resource contains an instance of a ProjectPhaseDefinition within a project which can then have project specific dates.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project phase does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view project phase
     * @param id Project phase id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getProjectPhaseWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getProjectPhaseRequestCreation(id);
    }
}
