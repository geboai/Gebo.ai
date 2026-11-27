package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCiCatalogResourcesVersion;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdCatalogPublishRequest;

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
public class CiCatalogApi {
    private ApiClient apiClient;

    public CiCatalogApi() {
        this(new ApiClient());
    }

    public CiCatalogApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Publish a new component project release as version to the CI/CD catalog
     * Publishes a release of a catalog resource as version to the CI/CD catalog.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdCatalogPublishRequest The postApiV4ProjectsIdCatalogPublishRequest parameter
     * @return APIEntitiesCiCatalogResourcesVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdCatalogPublishRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdCatalogPublishRequest postApiV4ProjectsIdCatalogPublishRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdCatalogPublish", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdCatalogPublishRequest' is set
        if (postApiV4ProjectsIdCatalogPublishRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdCatalogPublishRequest' when calling postApiV4ProjectsIdCatalogPublish", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCiCatalogResourcesVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/catalog/publish", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdCatalogPublishRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Publish a new component project release as version to the CI/CD catalog
     * Publishes a release of a catalog resource as version to the CI/CD catalog.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdCatalogPublishRequest The postApiV4ProjectsIdCatalogPublishRequest parameter
     * @return APIEntitiesCiCatalogResourcesVersion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCiCatalogResourcesVersion postApiV4ProjectsIdCatalogPublish(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdCatalogPublishRequest postApiV4ProjectsIdCatalogPublishRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiCatalogResourcesVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdCatalogPublishRequestCreation(id, postApiV4ProjectsIdCatalogPublishRequest).body(localVarReturnType);
    }

    /**
     * Publish a new component project release as version to the CI/CD catalog
     * Publishes a release of a catalog resource as version to the CI/CD catalog.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdCatalogPublishRequest The postApiV4ProjectsIdCatalogPublishRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCiCatalogResourcesVersion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCiCatalogResourcesVersion> postApiV4ProjectsIdCatalogPublishWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdCatalogPublishRequest postApiV4ProjectsIdCatalogPublishRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCiCatalogResourcesVersion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdCatalogPublishRequestCreation(id, postApiV4ProjectsIdCatalogPublishRequest).toEntity(localVarReturnType);
    }

    /**
     * Publish a new component project release as version to the CI/CD catalog
     * Publishes a release of a catalog resource as version to the CI/CD catalog.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdCatalogPublishRequest The postApiV4ProjectsIdCatalogPublishRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdCatalogPublishWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdCatalogPublishRequest postApiV4ProjectsIdCatalogPublishRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdCatalogPublishRequestCreation(id, postApiV4ProjectsIdCatalogPublishRequest);
    }
}
