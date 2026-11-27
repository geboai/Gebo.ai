package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesPlanLimit;
import ai.gebo.gitlab.client.model.PutApiV4ApplicationPlanLimitsRequest;

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
public class PlanLimitsApi {
    private ApiClient apiClient;

    public PlanLimitsApi() {
        this(new ApiClient());
    }

    public PlanLimitsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get current plan limits
     * List the current limits of a plan on the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param planName Name of the plan to get the limits from.
     * @return APIEntitiesPlanLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ApplicationPlanLimitsRequestCreation(@jakarta.annotation.Nullable String planName) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "plan_name", planName));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPlanLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/application/plan_limits", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get current plan limits
     * List the current limits of a plan on the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param planName Name of the plan to get the limits from.
     * @return APIEntitiesPlanLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPlanLimit getApiV4ApplicationPlanLimits(@jakarta.annotation.Nullable String planName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPlanLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationPlanLimitsRequestCreation(planName).body(localVarReturnType);
    }

    /**
     * Get current plan limits
     * List the current limits of a plan on the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param planName Name of the plan to get the limits from.
     * @return ResponseEntity&lt;APIEntitiesPlanLimit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPlanLimit> getApiV4ApplicationPlanLimitsWithHttpInfo(@jakarta.annotation.Nullable String planName) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPlanLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationPlanLimitsRequestCreation(planName).toEntity(localVarReturnType);
    }

    /**
     * Get current plan limits
     * List the current limits of a plan on the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>400</b> - Bad Request
     * @param planName Name of the plan to get the limits from.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ApplicationPlanLimitsWithResponseSpec(@jakarta.annotation.Nullable String planName) throws RestClientResponseException {
        return getApiV4ApplicationPlanLimitsRequestCreation(planName);
    }

    /**
     * Change plan limits
     * Modify the limits of a plan on the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param putApiV4ApplicationPlanLimitsRequest The putApiV4ApplicationPlanLimitsRequest parameter
     * @return APIEntitiesPlanLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ApplicationPlanLimitsRequestCreation(@jakarta.annotation.Nonnull PutApiV4ApplicationPlanLimitsRequest putApiV4ApplicationPlanLimitsRequest) throws RestClientResponseException {
        // verify the required parameter 'putApiV4ApplicationPlanLimitsRequest' is set
        if (putApiV4ApplicationPlanLimitsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ApplicationPlanLimitsRequest' when calling putApiV4ApplicationPlanLimits", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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

        ParameterizedTypeReference<APIEntitiesPlanLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/application/plan_limits", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ApplicationPlanLimitsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Change plan limits
     * Modify the limits of a plan on the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param putApiV4ApplicationPlanLimitsRequest The putApiV4ApplicationPlanLimitsRequest parameter
     * @return APIEntitiesPlanLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPlanLimit putApiV4ApplicationPlanLimits(@jakarta.annotation.Nonnull PutApiV4ApplicationPlanLimitsRequest putApiV4ApplicationPlanLimitsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPlanLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ApplicationPlanLimitsRequestCreation(putApiV4ApplicationPlanLimitsRequest).body(localVarReturnType);
    }

    /**
     * Change plan limits
     * Modify the limits of a plan on the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param putApiV4ApplicationPlanLimitsRequest The putApiV4ApplicationPlanLimitsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPlanLimit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPlanLimit> putApiV4ApplicationPlanLimitsWithHttpInfo(@jakarta.annotation.Nonnull PutApiV4ApplicationPlanLimitsRequest putApiV4ApplicationPlanLimitsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPlanLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ApplicationPlanLimitsRequestCreation(putApiV4ApplicationPlanLimitsRequest).toEntity(localVarReturnType);
    }

    /**
     * Change plan limits
     * Modify the limits of a plan on the GitLab instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param putApiV4ApplicationPlanLimitsRequest The putApiV4ApplicationPlanLimitsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ApplicationPlanLimitsWithResponseSpec(@jakarta.annotation.Nonnull PutApiV4ApplicationPlanLimitsRequest putApiV4ApplicationPlanLimitsRequest) throws RestClientResponseException {
        return putApiV4ApplicationPlanLimitsRequestCreation(putApiV4ApplicationPlanLimitsRequest);
    }
}
