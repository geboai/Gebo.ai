package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdSecurityScansSastSastEndpointRequest;

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
public class SecurityScansApi {
    private ApiClient apiClient;

    public SecurityScansApi() {
        this(new ApiClient());
    }

    public SecurityScansApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Scan a file for vulnerabilities
     * Scans a specified file for security vulnerabilities using SAST.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param sastEndpoint Name of the SAST scanner service endpoint to call.
     * @param postApiV4ProjectsIdSecurityScansSastSastEndpointRequest The postApiV4ProjectsIdSecurityScansSastSastEndpointRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdSecurityScansSastSastEndpointRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sastEndpoint, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSecurityScansSastSastEndpointRequest postApiV4ProjectsIdSecurityScansSastSastEndpointRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdSecurityScansSastSastEndpoint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sastEndpoint' is set
        if (sastEndpoint == null) {
            throw new RestClientResponseException("Missing the required parameter 'sastEndpoint' when calling postApiV4ProjectsIdSecurityScansSastSastEndpoint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdSecurityScansSastSastEndpointRequest' is set
        if (postApiV4ProjectsIdSecurityScansSastSastEndpointRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdSecurityScansSastSastEndpointRequest' when calling postApiV4ProjectsIdSecurityScansSastSastEndpoint", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("sast_endpoint", sastEndpoint);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/security_scans/sast/{sast_endpoint}", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdSecurityScansSastSastEndpointRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Scan a file for vulnerabilities
     * Scans a specified file for security vulnerabilities using SAST.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param sastEndpoint Name of the SAST scanner service endpoint to call.
     * @param postApiV4ProjectsIdSecurityScansSastSastEndpointRequest The postApiV4ProjectsIdSecurityScansSastSastEndpointRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdSecurityScansSastSastEndpoint(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sastEndpoint, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSecurityScansSastSastEndpointRequest postApiV4ProjectsIdSecurityScansSastSastEndpointRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdSecurityScansSastSastEndpointRequestCreation(id, sastEndpoint, postApiV4ProjectsIdSecurityScansSastSastEndpointRequest).body(localVarReturnType);
    }

    /**
     * Scan a file for vulnerabilities
     * Scans a specified file for security vulnerabilities using SAST.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param sastEndpoint Name of the SAST scanner service endpoint to call.
     * @param postApiV4ProjectsIdSecurityScansSastSastEndpointRequest The postApiV4ProjectsIdSecurityScansSastSastEndpointRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdSecurityScansSastSastEndpointWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sastEndpoint, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSecurityScansSastSastEndpointRequest postApiV4ProjectsIdSecurityScansSastSastEndpointRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdSecurityScansSastSastEndpointRequestCreation(id, sastEndpoint, postApiV4ProjectsIdSecurityScansSastSastEndpointRequest).toEntity(localVarReturnType);
    }

    /**
     * Scan a file for vulnerabilities
     * Scans a specified file for security vulnerabilities using SAST.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param sastEndpoint Name of the SAST scanner service endpoint to call.
     * @param postApiV4ProjectsIdSecurityScansSastSastEndpointRequest The postApiV4ProjectsIdSecurityScansSastSastEndpointRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdSecurityScansSastSastEndpointWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String sastEndpoint, @jakarta.annotation.Nonnull PostApiV4ProjectsIdSecurityScansSastSastEndpointRequest postApiV4ProjectsIdSecurityScansSastSastEndpointRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdSecurityScansSastSastEndpointRequestCreation(id, sastEndpoint, postApiV4ProjectsIdSecurityScansSastSastEndpointRequest);
    }
}
