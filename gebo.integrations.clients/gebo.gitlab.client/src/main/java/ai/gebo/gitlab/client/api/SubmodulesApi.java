package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCommitDetail;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest;

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
public class SubmodulesApi {
    private ApiClient apiClient;

    public SubmodulesApi() {
        this(new ApiClient());
    }

    public SubmodulesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Update a submodule reference
     * Updates a reference for a specified submodule. Used in workflows, especially automated ones, to keep other projects that use the submodule up to date.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - The repository is empty
     * @param id ID or URL-encoded path of the project.
     * @param submodule URL-encoded full path to the submodule, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest The putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest parameter
     * @return APIEntitiesCommitDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String submodule, @jakarta.annotation.Nonnull PutApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdRepositorySubmodulesSubmodule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'submodule' is set
        if (submodule == null) {
            throw new RestClientResponseException("Missing the required parameter 'submodule' when calling putApiV4ProjectsIdRepositorySubmodulesSubmodule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest' is set
        if (putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest' when calling putApiV4ProjectsIdRepositorySubmodulesSubmodule", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("submodule", submodule);

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

        ParameterizedTypeReference<APIEntitiesCommitDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/repository/submodules/{submodule}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a submodule reference
     * Updates a reference for a specified submodule. Used in workflows, especially automated ones, to keep other projects that use the submodule up to date.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - The repository is empty
     * @param id ID or URL-encoded path of the project.
     * @param submodule URL-encoded full path to the submodule, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest The putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest parameter
     * @return APIEntitiesCommitDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCommitDetail putApiV4ProjectsIdRepositorySubmodulesSubmodule(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String submodule, @jakarta.annotation.Nonnull PutApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequestCreation(id, submodule, putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest).body(localVarReturnType);
    }

    /**
     * Update a submodule reference
     * Updates a reference for a specified submodule. Used in workflows, especially automated ones, to keep other projects that use the submodule up to date.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - The repository is empty
     * @param id ID or URL-encoded path of the project.
     * @param submodule URL-encoded full path to the submodule, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest The putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCommitDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCommitDetail> putApiV4ProjectsIdRepositorySubmodulesSubmoduleWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String submodule, @jakarta.annotation.Nonnull PutApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCommitDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequestCreation(id, submodule, putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a submodule reference
     * Updates a reference for a specified submodule. Used in workflows, especially automated ones, to keep other projects that use the submodule up to date.
     * <p><b>200</b> - OK
     * <p><b>404</b> - 404 Project Not Found
     * <p><b>401</b> - 401 Unauthorized
     * <p><b>400</b> - The repository is empty
     * @param id ID or URL-encoded path of the project.
     * @param submodule URL-encoded full path to the submodule, for example &#x60;lib%2Fclass%2Erb&#x60;.
     * @param putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest The putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdRepositorySubmodulesSubmoduleWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull String submodule, @jakarta.annotation.Nonnull PutApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequestCreation(id, submodule, putApiV4ProjectsIdRepositorySubmodulesSubmoduleRequest);
    }
}
