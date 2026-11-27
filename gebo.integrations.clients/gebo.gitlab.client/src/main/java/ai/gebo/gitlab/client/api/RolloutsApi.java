package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesCdRollout;
import ai.gebo.gitlab.client.model.PostApiV4RolloutsIdRequest;

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
public class RolloutsApi {
    private ApiClient apiClient;

    public RolloutsApi() {
        this(new ApiClient());
    }

    public RolloutsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Ingest a rollout workflow event
     * Reports flow-graph progress from the CD orchestrator (a Starlark workflow run by AutoFlow).
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id The ID of the rollout
     * @param postApiV4RolloutsIdRequest The postApiV4RolloutsIdRequest parameter
     * @return APIEntitiesCdRollout
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4RolloutsIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4RolloutsIdRequest postApiV4RolloutsIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4RolloutsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesCdRollout> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/rollouts/{id}", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4RolloutsIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Ingest a rollout workflow event
     * Reports flow-graph progress from the CD orchestrator (a Starlark workflow run by AutoFlow).
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id The ID of the rollout
     * @param postApiV4RolloutsIdRequest The postApiV4RolloutsIdRequest parameter
     * @return APIEntitiesCdRollout
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesCdRollout postApiV4RolloutsId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4RolloutsIdRequest postApiV4RolloutsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCdRollout> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RolloutsIdRequestCreation(id, postApiV4RolloutsIdRequest).body(localVarReturnType);
    }

    /**
     * Ingest a rollout workflow event
     * Reports flow-graph progress from the CD orchestrator (a Starlark workflow run by AutoFlow).
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id The ID of the rollout
     * @param postApiV4RolloutsIdRequest The postApiV4RolloutsIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesCdRollout&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesCdRollout> postApiV4RolloutsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4RolloutsIdRequest postApiV4RolloutsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesCdRollout> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4RolloutsIdRequestCreation(id, postApiV4RolloutsIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Ingest a rollout workflow event
     * Reports flow-graph progress from the CD orchestrator (a Starlark workflow run by AutoFlow).
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id The ID of the rollout
     * @param postApiV4RolloutsIdRequest The postApiV4RolloutsIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4RolloutsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PostApiV4RolloutsIdRequest postApiV4RolloutsIdRequest) throws RestClientResponseException {
        return postApiV4RolloutsIdRequestCreation(id, postApiV4RolloutsIdRequest);
    }
}
