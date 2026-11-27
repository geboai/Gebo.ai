package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.HourlyRateCollectionModel;
import ai.gebo.openproject.client.model.HourlyRateModel;

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
public class HourlyRatesApi {
    private ApiClient apiClient;

    public HourlyRatesApi() {
        this(new ApiClient());
    }

    public HourlyRatesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create an hourly rate for a principal
     * Creates an hourly rate for the given principal, taking effect from &#x60;validFrom&#x60;.  Linking a &#x60;project&#x60; creates a rate for that project. Omitting the link creates the principal&#39;s default rate, which applies in every project where no project rate is set up.  Creating a rate recalculates the costs of time entries and budgets already booked in the period the rate covers.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the linked project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the linked project, or &#x60;manage_default_hourly_rates&#x60; globally when creating a default rate.
     * <p><b>404</b> - Returned if the principal does not exist, is not visible to the requesting user, or the requesting user may not see their rates.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters.
     * @param id Principal id.
     * @param hourlyRateModel The hourlyRateModel parameter
     * @return HourlyRateModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createPrincipalHourlyRateRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable HourlyRateModel hourlyRateModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createPrincipalHourlyRate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<HourlyRateModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/principals/{id}/hourly_rates", HttpMethod.POST, pathParams, localVarQueryParams, hourlyRateModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an hourly rate for a principal
     * Creates an hourly rate for the given principal, taking effect from &#x60;validFrom&#x60;.  Linking a &#x60;project&#x60; creates a rate for that project. Omitting the link creates the principal&#39;s default rate, which applies in every project where no project rate is set up.  Creating a rate recalculates the costs of time entries and budgets already booked in the period the rate covers.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the linked project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the linked project, or &#x60;manage_default_hourly_rates&#x60; globally when creating a default rate.
     * <p><b>404</b> - Returned if the principal does not exist, is not visible to the requesting user, or the requesting user may not see their rates.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters.
     * @param id Principal id.
     * @param hourlyRateModel The hourlyRateModel parameter
     * @return HourlyRateModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public HourlyRateModel createPrincipalHourlyRate(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable HourlyRateModel hourlyRateModel) throws RestClientResponseException {
        ParameterizedTypeReference<HourlyRateModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createPrincipalHourlyRateRequestCreation(id, hourlyRateModel).body(localVarReturnType);
    }

    /**
     * Create an hourly rate for a principal
     * Creates an hourly rate for the given principal, taking effect from &#x60;validFrom&#x60;.  Linking a &#x60;project&#x60; creates a rate for that project. Omitting the link creates the principal&#39;s default rate, which applies in every project where no project rate is set up.  Creating a rate recalculates the costs of time entries and budgets already booked in the period the rate covers.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the linked project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the linked project, or &#x60;manage_default_hourly_rates&#x60; globally when creating a default rate.
     * <p><b>404</b> - Returned if the principal does not exist, is not visible to the requesting user, or the requesting user may not see their rates.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters.
     * @param id Principal id.
     * @param hourlyRateModel The hourlyRateModel parameter
     * @return ResponseEntity&lt;HourlyRateModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HourlyRateModel> createPrincipalHourlyRateWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable HourlyRateModel hourlyRateModel) throws RestClientResponseException {
        ParameterizedTypeReference<HourlyRateModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createPrincipalHourlyRateRequestCreation(id, hourlyRateModel).toEntity(localVarReturnType);
    }

    /**
     * Create an hourly rate for a principal
     * Creates an hourly rate for the given principal, taking effect from &#x60;validFrom&#x60;.  Linking a &#x60;project&#x60; creates a rate for that project. Omitting the link creates the principal&#39;s default rate, which applies in every project where no project rate is set up.  Creating a rate recalculates the costs of time entries and budgets already booked in the period the rate covers.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the linked project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the linked project, or &#x60;manage_default_hourly_rates&#x60; globally when creating a default rate.
     * <p><b>404</b> - Returned if the principal does not exist, is not visible to the requesting user, or the requesting user may not see their rates.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters.
     * @param id Principal id.
     * @param hourlyRateModel The hourlyRateModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createPrincipalHourlyRateWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable HourlyRateModel hourlyRateModel) throws RestClientResponseException {
        return createPrincipalHourlyRateRequestCreation(id, hourlyRateModel);
    }

    /**
     * Delete an hourly rate
     * Deletes the given hourly rate. The costs of time entries and budgets booked in the period the rate covered are recalculated with the rate that takes over.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the rate&#39;s project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>204</b> - No Content. The rate was deleted successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the rate&#39;s project, or &#x60;manage_default_hourly_rates&#x60; globally for a default rate.
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deletePrincipalHourlyRateRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deletePrincipalHourlyRate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'rateId' is set
        if (rateId == null) {
            throw new RestClientResponseException("Missing the required parameter 'rateId' when calling deletePrincipalHourlyRate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("rate_id", rateId);

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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/principals/{id}/hourly_rates/{rate_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an hourly rate
     * Deletes the given hourly rate. The costs of time entries and budgets booked in the period the rate covered are recalculated with the rate that takes over.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the rate&#39;s project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>204</b> - No Content. The rate was deleted successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the rate&#39;s project, or &#x60;manage_default_hourly_rates&#x60; globally for a default rate.
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deletePrincipalHourlyRate(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deletePrincipalHourlyRateRequestCreation(id, rateId).body(localVarReturnType);
    }

    /**
     * Delete an hourly rate
     * Deletes the given hourly rate. The costs of time entries and budgets booked in the period the rate covered are recalculated with the rate that takes over.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the rate&#39;s project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>204</b> - No Content. The rate was deleted successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the rate&#39;s project, or &#x60;manage_default_hourly_rates&#x60; globally for a default rate.
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deletePrincipalHourlyRateWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deletePrincipalHourlyRateRequestCreation(id, rateId).toEntity(localVarReturnType);
    }

    /**
     * Delete an hourly rate
     * Deletes the given hourly rate. The costs of time entries and budgets booked in the period the rate covered are recalculated with the rate that takes over.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the rate&#39;s project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>204</b> - No Content. The rate was deleted successfully.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the rate&#39;s project, or &#x60;manage_default_hourly_rates&#x60; globally for a default rate.
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deletePrincipalHourlyRateWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId) throws RestClientResponseException {
        return deletePrincipalHourlyRateRequestCreation(id, rateId);
    }

    /**
     * View an hourly rate
     * Returns a single hourly rate of the given principal.  **Required permissions:** - Reaching a principal&#39;s rates at all requires &#x60;view_hourly_rates&#x60; in at least one project,   unless the principal is the requesting user. - A project rate additionally requires &#x60;view_hourly_rates&#x60; in the project it applies in, or   &#x60;view_own_hourly_rate&#x60; there when the rate belongs to the requesting user. - A default rate belongs to no project and is readable as soon as the principal&#39;s rates are reachable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @return HourlyRateModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getPrincipalHourlyRateRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getPrincipalHourlyRate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'rateId' is set
        if (rateId == null) {
            throw new RestClientResponseException("Missing the required parameter 'rateId' when calling getPrincipalHourlyRate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("rate_id", rateId);

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

        ParameterizedTypeReference<HourlyRateModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/principals/{id}/hourly_rates/{rate_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View an hourly rate
     * Returns a single hourly rate of the given principal.  **Required permissions:** - Reaching a principal&#39;s rates at all requires &#x60;view_hourly_rates&#x60; in at least one project,   unless the principal is the requesting user. - A project rate additionally requires &#x60;view_hourly_rates&#x60; in the project it applies in, or   &#x60;view_own_hourly_rate&#x60; there when the rate belongs to the requesting user. - A default rate belongs to no project and is readable as soon as the principal&#39;s rates are reachable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @return HourlyRateModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public HourlyRateModel getPrincipalHourlyRate(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId) throws RestClientResponseException {
        ParameterizedTypeReference<HourlyRateModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getPrincipalHourlyRateRequestCreation(id, rateId).body(localVarReturnType);
    }

    /**
     * View an hourly rate
     * Returns a single hourly rate of the given principal.  **Required permissions:** - Reaching a principal&#39;s rates at all requires &#x60;view_hourly_rates&#x60; in at least one project,   unless the principal is the requesting user. - A project rate additionally requires &#x60;view_hourly_rates&#x60; in the project it applies in, or   &#x60;view_own_hourly_rate&#x60; there when the rate belongs to the requesting user. - A default rate belongs to no project and is readable as soon as the principal&#39;s rates are reachable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @return ResponseEntity&lt;HourlyRateModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HourlyRateModel> getPrincipalHourlyRateWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId) throws RestClientResponseException {
        ParameterizedTypeReference<HourlyRateModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getPrincipalHourlyRateRequestCreation(id, rateId).toEntity(localVarReturnType);
    }

    /**
     * View an hourly rate
     * Returns a single hourly rate of the given principal.  **Required permissions:** - Reaching a principal&#39;s rates at all requires &#x60;view_hourly_rates&#x60; in at least one project,   unless the principal is the requesting user. - A project rate additionally requires &#x60;view_hourly_rates&#x60; in the project it applies in, or   &#x60;view_own_hourly_rate&#x60; there when the rate belongs to the requesting user. - A default rate belongs to no project and is readable as soon as the principal&#39;s rates are reachable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getPrincipalHourlyRateWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId) throws RestClientResponseException {
        return getPrincipalHourlyRateRequestCreation(id, rateId);
    }

    /**
     * List hourly rates of a principal
     * Lists the hourly rates of the given principal that are visible to the requesting user, ordered by &#x60;validFrom&#x60; descending.  The collection mixes both kinds of rate. A rate linking a &#x60;project&#x60; applies in that project only, a rate without one is the principal&#39;s default rate and applies wherever no project rate does.  **Required permissions:** - Reaching a principal&#39;s rates at all requires &#x60;view_hourly_rates&#x60; in at least one project,   unless the principal is the requesting user. - Project rates are limited to the projects granting the requesting user &#x60;view_hourly_rates&#x60;,   plus, for their own rates, the projects granting them &#x60;view_own_hourly_rate&#x60;. - Default rates belong to no project and are returned as soon as the principal&#39;s rates are reachable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the principal does not exist, is not visible to the requesting user, or the requesting user may not see their rates.
     * @param id Principal id.
     * @return HourlyRateCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listPrincipalHourlyRatesRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listPrincipalHourlyRates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<HourlyRateCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/principals/{id}/hourly_rates", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List hourly rates of a principal
     * Lists the hourly rates of the given principal that are visible to the requesting user, ordered by &#x60;validFrom&#x60; descending.  The collection mixes both kinds of rate. A rate linking a &#x60;project&#x60; applies in that project only, a rate without one is the principal&#39;s default rate and applies wherever no project rate does.  **Required permissions:** - Reaching a principal&#39;s rates at all requires &#x60;view_hourly_rates&#x60; in at least one project,   unless the principal is the requesting user. - Project rates are limited to the projects granting the requesting user &#x60;view_hourly_rates&#x60;,   plus, for their own rates, the projects granting them &#x60;view_own_hourly_rate&#x60;. - Default rates belong to no project and are returned as soon as the principal&#39;s rates are reachable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the principal does not exist, is not visible to the requesting user, or the requesting user may not see their rates.
     * @param id Principal id.
     * @return HourlyRateCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public HourlyRateCollectionModel listPrincipalHourlyRates(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<HourlyRateCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listPrincipalHourlyRatesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List hourly rates of a principal
     * Lists the hourly rates of the given principal that are visible to the requesting user, ordered by &#x60;validFrom&#x60; descending.  The collection mixes both kinds of rate. A rate linking a &#x60;project&#x60; applies in that project only, a rate without one is the principal&#39;s default rate and applies wherever no project rate does.  **Required permissions:** - Reaching a principal&#39;s rates at all requires &#x60;view_hourly_rates&#x60; in at least one project,   unless the principal is the requesting user. - Project rates are limited to the projects granting the requesting user &#x60;view_hourly_rates&#x60;,   plus, for their own rates, the projects granting them &#x60;view_own_hourly_rate&#x60;. - Default rates belong to no project and are returned as soon as the principal&#39;s rates are reachable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the principal does not exist, is not visible to the requesting user, or the requesting user may not see their rates.
     * @param id Principal id.
     * @return ResponseEntity&lt;HourlyRateCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HourlyRateCollectionModel> listPrincipalHourlyRatesWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<HourlyRateCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listPrincipalHourlyRatesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List hourly rates of a principal
     * Lists the hourly rates of the given principal that are visible to the requesting user, ordered by &#x60;validFrom&#x60; descending.  The collection mixes both kinds of rate. A rate linking a &#x60;project&#x60; applies in that project only, a rate without one is the principal&#39;s default rate and applies wherever no project rate does.  **Required permissions:** - Reaching a principal&#39;s rates at all requires &#x60;view_hourly_rates&#x60; in at least one project,   unless the principal is the requesting user. - Project rates are limited to the projects granting the requesting user &#x60;view_hourly_rates&#x60;,   plus, for their own rates, the projects granting them &#x60;view_own_hourly_rate&#x60;. - Default rates belong to no project and are returned as soon as the principal&#39;s rates are reachable.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the principal does not exist, is not visible to the requesting user, or the requesting user may not see their rates.
     * @param id Principal id.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listPrincipalHourlyRatesWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listPrincipalHourlyRatesRequestCreation(id);
    }

    /**
     * Update an hourly rate
     * Updates &#x60;validFrom&#x60; or &#x60;rate&#x60; of the given hourly rate.  The &#x60;project&#x60; link can only be written when creating a rate. A rate can neither be moved from one project to another, nor be turned from a project rate into a default rate or the other way around.  Changing a rate recalculates the costs of time entries and budgets already booked in the period the rate covers.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the rate&#39;s project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the rate&#39;s project, or &#x60;manage_default_hourly_rates&#x60; globally for a default rate.
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if it tries to write the &#x60;project&#x60; link, which is read-only once the rate exists.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @param hourlyRateModel The hourlyRateModel parameter
     * @return HourlyRateModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updatePrincipalHourlyRateRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId, @jakarta.annotation.Nullable HourlyRateModel hourlyRateModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updatePrincipalHourlyRate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'rateId' is set
        if (rateId == null) {
            throw new RestClientResponseException("Missing the required parameter 'rateId' when calling updatePrincipalHourlyRate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("rate_id", rateId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<HourlyRateModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/principals/{id}/hourly_rates/{rate_id}", HttpMethod.PATCH, pathParams, localVarQueryParams, hourlyRateModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an hourly rate
     * Updates &#x60;validFrom&#x60; or &#x60;rate&#x60; of the given hourly rate.  The &#x60;project&#x60; link can only be written when creating a rate. A rate can neither be moved from one project to another, nor be turned from a project rate into a default rate or the other way around.  Changing a rate recalculates the costs of time entries and budgets already booked in the period the rate covers.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the rate&#39;s project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the rate&#39;s project, or &#x60;manage_default_hourly_rates&#x60; globally for a default rate.
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if it tries to write the &#x60;project&#x60; link, which is read-only once the rate exists.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @param hourlyRateModel The hourlyRateModel parameter
     * @return HourlyRateModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public HourlyRateModel updatePrincipalHourlyRate(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId, @jakarta.annotation.Nullable HourlyRateModel hourlyRateModel) throws RestClientResponseException {
        ParameterizedTypeReference<HourlyRateModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updatePrincipalHourlyRateRequestCreation(id, rateId, hourlyRateModel).body(localVarReturnType);
    }

    /**
     * Update an hourly rate
     * Updates &#x60;validFrom&#x60; or &#x60;rate&#x60; of the given hourly rate.  The &#x60;project&#x60; link can only be written when creating a rate. A rate can neither be moved from one project to another, nor be turned from a project rate into a default rate or the other way around.  Changing a rate recalculates the costs of time entries and budgets already booked in the period the rate covers.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the rate&#39;s project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the rate&#39;s project, or &#x60;manage_default_hourly_rates&#x60; globally for a default rate.
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if it tries to write the &#x60;project&#x60; link, which is read-only once the rate exists.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @param hourlyRateModel The hourlyRateModel parameter
     * @return ResponseEntity&lt;HourlyRateModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HourlyRateModel> updatePrincipalHourlyRateWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId, @jakarta.annotation.Nullable HourlyRateModel hourlyRateModel) throws RestClientResponseException {
        ParameterizedTypeReference<HourlyRateModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updatePrincipalHourlyRateRequestCreation(id, rateId, hourlyRateModel).toEntity(localVarReturnType);
    }

    /**
     * Update an hourly rate
     * Updates &#x60;validFrom&#x60; or &#x60;rate&#x60; of the given hourly rate.  The &#x60;project&#x60; link can only be written when creating a rate. A rate can neither be moved from one project to another, nor be turned from a project rate into a default rate or the other way around.  Changing a rate recalculates the costs of time entries and budgets already booked in the period the rate covers.  **Required permissions:** - A project rate requires &#x60;edit_hourly_rates&#x60; in the rate&#39;s project, or &#x60;edit_own_hourly_rate&#x60;   in it when the rate is the requesting user&#39;s own. - A default rate requires the global &#x60;manage_default_hourly_rates&#x60; permission.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** &#x60;edit_hourly_rates&#x60; or &#x60;edit_own_hourly_rate&#x60; in the rate&#39;s project, or &#x60;manage_default_hourly_rates&#x60; globally for a default rate.
     * <p><b>404</b> - Returned if the principal or the rate does not exist, or the requesting user may not see it.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if the request body contains invalid parameters, or if it tries to write the &#x60;project&#x60; link, which is read-only once the rate exists.
     * @param id Principal id.
     * @param rateId Hourly rate id.
     * @param hourlyRateModel The hourlyRateModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updatePrincipalHourlyRateWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer rateId, @jakarta.annotation.Nullable HourlyRateModel hourlyRateModel) throws RestClientResponseException {
        return updatePrincipalHourlyRateRequestCreation(id, rateId, hourlyRateModel);
    }
}
