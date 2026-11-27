package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesMetadata;

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
public class MetadataApi {
    private ApiClient apiClient;

    public MetadataApi() {
        this(new ApiClient());
    }

    public MetadataApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve metadata information for this GitLab instance
     * Retrieves metadata information for the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4MetadataRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/metadata", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve metadata information for this GitLab instance
     * Retrieves metadata information for the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMetadata getApiV4Metadata() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4MetadataRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve metadata information for this GitLab instance
     * Retrieves metadata information for the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseEntity&lt;APIEntitiesMetadata&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMetadata> getApiV4MetadataWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4MetadataRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve metadata information for this GitLab instance
     * Retrieves metadata information for the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4MetadataWithResponseSpec() throws RestClientResponseException {
        return getApiV4MetadataRequestCreation();
    }

    /**
     * Retrieves version information for the GitLab instance
     * Retrieves metadata information for the GitLab instance. Deprecated. Use &#x60;GET /metadata&#x60; instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4VersionRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/version", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieves version information for the GitLab instance
     * Retrieves metadata information for the GitLab instance. Deprecated. Use &#x60;GET /metadata&#x60; instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesMetadata
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMetadata getApiV4Version() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VersionRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieves version information for the GitLab instance
     * Retrieves metadata information for the GitLab instance. Deprecated. Use &#x60;GET /metadata&#x60; instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseEntity&lt;APIEntitiesMetadata&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMetadata> getApiV4VersionWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetadata> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4VersionRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieves version information for the GitLab instance
     * Retrieves metadata information for the GitLab instance. Deprecated. Use &#x60;GET /metadata&#x60; instead.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4VersionWithResponseSpec() throws RestClientResponseException {
        return getApiV4VersionRequestCreation();
    }
}
