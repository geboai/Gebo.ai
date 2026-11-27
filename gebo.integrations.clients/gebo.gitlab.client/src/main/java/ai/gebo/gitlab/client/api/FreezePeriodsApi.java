package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesFreezePeriod;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdFreezePeriodsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest;

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
public class FreezePeriodsApi {
    private ApiClient apiClient;

    public FreezePeriodsApi() {
        this(new ApiClient());
    }

    public FreezePeriodsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a freeze period
     * Deletes a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @return APIEntitiesFreezePeriod
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdFreezePeriodsFreezePeriodId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'freezePeriodId' is set
        if (freezePeriodId == null) {
            throw new RestClientResponseException("Missing the required parameter 'freezePeriodId' when calling deleteApiV4ProjectsIdFreezePeriodsFreezePeriodId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("freeze_period_id", freezePeriodId);

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

        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/freeze_periods/{freeze_period_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a freeze period
     * Deletes a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @return APIEntitiesFreezePeriod
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFreezePeriod deleteApiV4ProjectsIdFreezePeriodsFreezePeriodId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(id, freezePeriodId).body(localVarReturnType);
    }

    /**
     * Delete a freeze period
     * Deletes a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @return ResponseEntity&lt;APIEntitiesFreezePeriod&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFreezePeriod> deleteApiV4ProjectsIdFreezePeriodsFreezePeriodIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(id, freezePeriodId).toEntity(localVarReturnType);
    }

    /**
     * Delete a freeze period
     * Deletes a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdFreezePeriodsFreezePeriodIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(id, freezePeriodId);
    }

    /**
     * List all freeze periods
     * Lists all freeze periods for a specified project, sorted by &#x60;created_at&#x60; in ascending order. You must have the Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesFreezePeriod&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdFreezePeriodsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdFreezePeriods", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesFreezePeriod>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/freeze_periods", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all freeze periods
     * Lists all freeze periods for a specified project, sorted by &#x60;created_at&#x60; in ascending order. You must have the Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesFreezePeriod&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesFreezePeriod> getApiV4ProjectsIdFreezePeriods(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFreezePeriod>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFreezePeriodsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all freeze periods
     * Lists all freeze periods for a specified project, sorted by &#x60;created_at&#x60; in ascending order. You must have the Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesFreezePeriod&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesFreezePeriod>> getApiV4ProjectsIdFreezePeriodsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFreezePeriod>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFreezePeriodsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all freeze periods
     * Lists all freeze periods for a specified project, sorted by &#x60;created_at&#x60; in ascending order. You must have the Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdFreezePeriodsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdFreezePeriodsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a freeze period
     * Retrieves a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @return APIEntitiesFreezePeriod
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdFreezePeriodsFreezePeriodId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'freezePeriodId' is set
        if (freezePeriodId == null) {
            throw new RestClientResponseException("Missing the required parameter 'freezePeriodId' when calling getApiV4ProjectsIdFreezePeriodsFreezePeriodId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("freeze_period_id", freezePeriodId);

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

        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/freeze_periods/{freeze_period_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a freeze period
     * Retrieves a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @return APIEntitiesFreezePeriod
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFreezePeriod getApiV4ProjectsIdFreezePeriodsFreezePeriodId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(id, freezePeriodId).body(localVarReturnType);
    }

    /**
     * Retrieve a freeze period
     * Retrieves a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @return ResponseEntity&lt;APIEntitiesFreezePeriod&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFreezePeriod> getApiV4ProjectsIdFreezePeriodsFreezePeriodIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(id, freezePeriodId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a freeze period
     * Retrieves a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Reporter, Developer, Maintainer, or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdFreezePeriodsFreezePeriodIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId) throws RestClientResponseException {
        return getApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(id, freezePeriodId);
    }

    /**
     * Create a freeze period
     * Creates a freeze period for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFreezePeriodsRequest The postApiV4ProjectsIdFreezePeriodsRequest parameter
     * @return APIEntitiesFreezePeriod
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdFreezePeriodsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFreezePeriodsRequest postApiV4ProjectsIdFreezePeriodsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdFreezePeriods", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdFreezePeriodsRequest' is set
        if (postApiV4ProjectsIdFreezePeriodsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdFreezePeriodsRequest' when calling postApiV4ProjectsIdFreezePeriods", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/freeze_periods", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdFreezePeriodsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a freeze period
     * Creates a freeze period for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFreezePeriodsRequest The postApiV4ProjectsIdFreezePeriodsRequest parameter
     * @return APIEntitiesFreezePeriod
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFreezePeriod postApiV4ProjectsIdFreezePeriods(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFreezePeriodsRequest postApiV4ProjectsIdFreezePeriodsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdFreezePeriodsRequestCreation(id, postApiV4ProjectsIdFreezePeriodsRequest).body(localVarReturnType);
    }

    /**
     * Create a freeze period
     * Creates a freeze period for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFreezePeriodsRequest The postApiV4ProjectsIdFreezePeriodsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesFreezePeriod&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFreezePeriod> postApiV4ProjectsIdFreezePeriodsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFreezePeriodsRequest postApiV4ProjectsIdFreezePeriodsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdFreezePeriodsRequestCreation(id, postApiV4ProjectsIdFreezePeriodsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a freeze period
     * Creates a freeze period for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdFreezePeriodsRequest The postApiV4ProjectsIdFreezePeriodsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdFreezePeriodsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdFreezePeriodsRequest postApiV4ProjectsIdFreezePeriodsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdFreezePeriodsRequestCreation(id, postApiV4ProjectsIdFreezePeriodsRequest);
    }

    /**
     * Update a freeze period
     * Updates a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @param putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest The putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest parameter
     * @return APIEntitiesFreezePeriod
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId, @jakarta.annotation.Nullable PutApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdFreezePeriodsFreezePeriodId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'freezePeriodId' is set
        if (freezePeriodId == null) {
            throw new RestClientResponseException("Missing the required parameter 'freezePeriodId' when calling putApiV4ProjectsIdFreezePeriodsFreezePeriodId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("freeze_period_id", freezePeriodId);

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

        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/freeze_periods/{freeze_period_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a freeze period
     * Updates a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @param putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest The putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest parameter
     * @return APIEntitiesFreezePeriod
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFreezePeriod putApiV4ProjectsIdFreezePeriodsFreezePeriodId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId, @jakarta.annotation.Nullable PutApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(id, freezePeriodId, putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest).body(localVarReturnType);
    }

    /**
     * Update a freeze period
     * Updates a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @param putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest The putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesFreezePeriod&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFreezePeriod> putApiV4ProjectsIdFreezePeriodsFreezePeriodIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId, @jakarta.annotation.Nullable PutApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFreezePeriod> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(id, freezePeriodId, putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a freeze period
     * Updates a freeze period for a specified &#x60;freeze_period_id&#x60;. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param freezePeriodId ID of the freeze period.
     * @param putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest The putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdFreezePeriodsFreezePeriodIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer freezePeriodId, @jakarta.annotation.Nullable PutApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequestCreation(id, freezePeriodId, putApiV4ProjectsIdFreezePeriodsFreezePeriodIdRequest);
    }
}
