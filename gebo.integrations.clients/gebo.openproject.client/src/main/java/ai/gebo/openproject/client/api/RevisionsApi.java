package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.RevisionModel;

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
public class RevisionsApi {
    private ApiClient apiClient;

    public RevisionsApi() {
        this(new ApiClient());
    }

    public RevisionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View revision
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the revision does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view changesets for the project the repository is created in.  *Note: A client without sufficient permissions shall not be able to test for the existence of a revision. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Revision id
     * @return RevisionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRevisionRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getRevision", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<RevisionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/revisions/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View revision
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the revision does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view changesets for the project the repository is created in.  *Note: A client without sufficient permissions shall not be able to test for the existence of a revision. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Revision id
     * @return RevisionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RevisionModel getRevision(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RevisionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRevisionRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View revision
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the revision does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view changesets for the project the repository is created in.  *Note: A client without sufficient permissions shall not be able to test for the existence of a revision. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Revision id
     * @return ResponseEntity&lt;RevisionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RevisionModel> getRevisionWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<RevisionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getRevisionRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View revision
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the revision does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view changesets for the project the repository is created in.  *Note: A client without sufficient permissions shall not be able to test for the existence of a revision. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id Revision id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getRevisionWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getRevisionRequestCreation(id);
    }
}
