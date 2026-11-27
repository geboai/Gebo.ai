package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.DayCollectionModel;
import ai.gebo.openproject.client.model.DayModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import java.time.LocalDate;
import ai.gebo.openproject.client.model.NonWorkingDayCollectionModel;
import ai.gebo.openproject.client.model.NonWorkingDayModel;
import ai.gebo.openproject.client.model.WeekDayCollectionModel;
import ai.gebo.openproject.client.model.WeekDayModel;

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
public class WorkScheduleApi {
    private ApiClient apiClient;

    public WorkScheduleApi() {
        this(new ApiClient());
    }

    public WorkScheduleApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * View day
     * View the day information for a given date.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * @param date The date of the non-working day to view in ISO 8601 format.
     * @return DayModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getDayRequestCreation(@jakarta.annotation.Nonnull LocalDate date) throws RestClientResponseException {
        // verify the required parameter 'date' is set
        if (date == null) {
            throw new RestClientResponseException("Missing the required parameter 'date' when calling getDay", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("date", date);

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

        ParameterizedTypeReference<DayModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/days/{date}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View day
     * View the day information for a given date.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * @param date The date of the non-working day to view in ISO 8601 format.
     * @return DayModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public DayModel getDay(@jakarta.annotation.Nonnull LocalDate date) throws RestClientResponseException {
        ParameterizedTypeReference<DayModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDayRequestCreation(date).body(localVarReturnType);
    }

    /**
     * View day
     * View the day information for a given date.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * @param date The date of the non-working day to view in ISO 8601 format.
     * @return ResponseEntity&lt;DayModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DayModel> getDayWithHttpInfo(@jakarta.annotation.Nonnull LocalDate date) throws RestClientResponseException {
        ParameterizedTypeReference<DayModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDayRequestCreation(date).toEntity(localVarReturnType);
    }

    /**
     * View day
     * View the day information for a given date.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * @param date The date of the non-working day to view in ISO 8601 format.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getDayWithResponseSpec(@jakarta.annotation.Nonnull LocalDate date) throws RestClientResponseException {
        return getDayRequestCreation(date);
    }

    /**
     * View a non-working day
     * Returns the non-working day information for a given date.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the given date is not a non-working day.
     * @param date The date of the non-working day to view in ISO 8601 format.
     * @return NonWorkingDayModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getNonWorkingDayRequestCreation(@jakarta.annotation.Nonnull LocalDate date) throws RestClientResponseException {
        // verify the required parameter 'date' is set
        if (date == null) {
            throw new RestClientResponseException("Missing the required parameter 'date' when calling getNonWorkingDay", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("date", date);

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

        ParameterizedTypeReference<NonWorkingDayModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/days/non_working/{date}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View a non-working day
     * Returns the non-working day information for a given date.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the given date is not a non-working day.
     * @param date The date of the non-working day to view in ISO 8601 format.
     * @return NonWorkingDayModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public NonWorkingDayModel getNonWorkingDay(@jakarta.annotation.Nonnull LocalDate date) throws RestClientResponseException {
        ParameterizedTypeReference<NonWorkingDayModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNonWorkingDayRequestCreation(date).body(localVarReturnType);
    }

    /**
     * View a non-working day
     * Returns the non-working day information for a given date.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the given date is not a non-working day.
     * @param date The date of the non-working day to view in ISO 8601 format.
     * @return ResponseEntity&lt;NonWorkingDayModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<NonWorkingDayModel> getNonWorkingDayWithHttpInfo(@jakarta.annotation.Nonnull LocalDate date) throws RestClientResponseException {
        ParameterizedTypeReference<NonWorkingDayModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNonWorkingDayRequestCreation(date).toEntity(localVarReturnType);
    }

    /**
     * View a non-working day
     * Returns the non-working day information for a given date.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the given date is not a non-working day.
     * @param date The date of the non-working day to view in ISO 8601 format.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getNonWorkingDayWithResponseSpec(@jakarta.annotation.Nonnull LocalDate date) throws RestClientResponseException {
        return getNonWorkingDayRequestCreation(date);
    }

    /**
     * View a week day
     * View a week day and its attributes.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the day is out of the 1-7 range.
     * @param day The week day from 1 to 7. 1 is Monday. 7 is Sunday.
     * @return WeekDayModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getWeekDayRequestCreation(@jakarta.annotation.Nonnull Integer day) throws RestClientResponseException {
        // verify the required parameter 'day' is set
        if (day == null) {
            throw new RestClientResponseException("Missing the required parameter 'day' when calling getWeekDay", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("day", day);

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

        ParameterizedTypeReference<WeekDayModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/days/week/{day}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View a week day
     * View a week day and its attributes.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the day is out of the 1-7 range.
     * @param day The week day from 1 to 7. 1 is Monday. 7 is Sunday.
     * @return WeekDayModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WeekDayModel getWeekDay(@jakarta.annotation.Nonnull Integer day) throws RestClientResponseException {
        ParameterizedTypeReference<WeekDayModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWeekDayRequestCreation(day).body(localVarReturnType);
    }

    /**
     * View a week day
     * View a week day and its attributes.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the day is out of the 1-7 range.
     * @param day The week day from 1 to 7. 1 is Monday. 7 is Sunday.
     * @return ResponseEntity&lt;WeekDayModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WeekDayModel> getWeekDayWithHttpInfo(@jakarta.annotation.Nonnull Integer day) throws RestClientResponseException {
        ParameterizedTypeReference<WeekDayModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWeekDayRequestCreation(day).toEntity(localVarReturnType);
    }

    /**
     * View a week day
     * View a week day and its attributes.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>404</b> - Returned if the day is out of the 1-7 range.
     * @param day The week day from 1 to 7. 1 is Monday. 7 is Sunday.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getWeekDayWithResponseSpec(@jakarta.annotation.Nonnull Integer day) throws RestClientResponseException {
        return getWeekDayRequestCreation(day);
    }

    /**
     * Lists days
     * Lists days information for a given date interval.  All days from the beginning of current month to the end of following month are returned by default.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions.  Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + date: the inclusive date interval to scope days to look up. When   unspecified, default is from the beginning of current month to the end   of following month.    Example: &#x60;{ \&quot;date\&quot;: { \&quot;operator\&quot;: \&quot;&lt;&gt;d\&quot;, \&quot;values\&quot;: [\&quot;2022-05-02\&quot;,\&quot;2022-05-26\&quot;] } }&#x60;   would return days between May 5 and May 26 2022, inclusive.  + working: when &#x60;true&#x60;, returns only the working days. When &#x60;false&#x60;,   returns only the non-working days (weekend days and non-working days).   When unspecified, returns both working and non-working days.    Example: &#x60;{ \&quot;working\&quot;: { \&quot;operator\&quot;: \&quot;&#x3D;\&quot;, \&quot;values\&quot;: [\&quot;t\&quot;] } }&#x60;   would exclude non-working days from the response.
     * @return DayCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listDaysRequestCreation(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<DayCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/days", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Lists days
     * Lists days information for a given date interval.  All days from the beginning of current month to the end of following month are returned by default.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions.  Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + date: the inclusive date interval to scope days to look up. When   unspecified, default is from the beginning of current month to the end   of following month.    Example: &#x60;{ \&quot;date\&quot;: { \&quot;operator\&quot;: \&quot;&lt;&gt;d\&quot;, \&quot;values\&quot;: [\&quot;2022-05-02\&quot;,\&quot;2022-05-26\&quot;] } }&#x60;   would return days between May 5 and May 26 2022, inclusive.  + working: when &#x60;true&#x60;, returns only the working days. When &#x60;false&#x60;,   returns only the non-working days (weekend days and non-working days).   When unspecified, returns both working and non-working days.    Example: &#x60;{ \&quot;working\&quot;: { \&quot;operator\&quot;: \&quot;&#x3D;\&quot;, \&quot;values\&quot;: [\&quot;t\&quot;] } }&#x60;   would exclude non-working days from the response.
     * @return DayCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public DayCollectionModel listDays(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<DayCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listDaysRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * Lists days
     * Lists days information for a given date interval.  All days from the beginning of current month to the end of following month are returned by default.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions.  Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + date: the inclusive date interval to scope days to look up. When   unspecified, default is from the beginning of current month to the end   of following month.    Example: &#x60;{ \&quot;date\&quot;: { \&quot;operator\&quot;: \&quot;&lt;&gt;d\&quot;, \&quot;values\&quot;: [\&quot;2022-05-02\&quot;,\&quot;2022-05-26\&quot;] } }&#x60;   would return days between May 5 and May 26 2022, inclusive.  + working: when &#x60;true&#x60;, returns only the working days. When &#x60;false&#x60;,   returns only the non-working days (weekend days and non-working days).   When unspecified, returns both working and non-working days.    Example: &#x60;{ \&quot;working\&quot;: { \&quot;operator\&quot;: \&quot;&#x3D;\&quot;, \&quot;values\&quot;: [\&quot;t\&quot;] } }&#x60;   would exclude non-working days from the response.
     * @return ResponseEntity&lt;DayCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DayCollectionModel> listDaysWithHttpInfo(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<DayCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listDaysRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * Lists days
     * Lists days information for a given date interval.  All days from the beginning of current month to the end of following month are returned by default.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions.  Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + date: the inclusive date interval to scope days to look up. When   unspecified, default is from the beginning of current month to the end   of following month.    Example: &#x60;{ \&quot;date\&quot;: { \&quot;operator\&quot;: \&quot;&lt;&gt;d\&quot;, \&quot;values\&quot;: [\&quot;2022-05-02\&quot;,\&quot;2022-05-26\&quot;] } }&#x60;   would return days between May 5 and May 26 2022, inclusive.  + working: when &#x60;true&#x60;, returns only the working days. When &#x60;false&#x60;,   returns only the non-working days (weekend days and non-working days).   When unspecified, returns both working and non-working days.    Example: &#x60;{ \&quot;working\&quot;: { \&quot;operator\&quot;: \&quot;&#x3D;\&quot;, \&quot;values\&quot;: [\&quot;t\&quot;] } }&#x60;   would exclude non-working days from the response.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listDaysWithResponseSpec(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listDaysRequestCreation(filters);
    }

    /**
     * Lists all non working days
     * Lists all one-time non working days, such as holidays. It does not lists the non working weekdays, such as each Saturday, Sunday. For listing the weekends, the &#x60;/api/v3/days&#x60; endpoint should be used.  All days from current year are returned by default.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions.  Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + date: the inclusive date interval to scope days to look up. When   unspecified, default is from the beginning to the end of current year.    Example: &#x60;{ \&quot;date\&quot;: { \&quot;operator\&quot;: \&quot;&lt;&gt;d\&quot;, \&quot;values\&quot;: [\&quot;2022-05-02\&quot;,\&quot;2022-05-26\&quot;] } }&#x60;   would return days between May 5 and May 26 2022, inclusive.
     * @return NonWorkingDayCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listNonWorkingDaysRequestCreation(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<NonWorkingDayCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/days/non_working", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Lists all non working days
     * Lists all one-time non working days, such as holidays. It does not lists the non working weekdays, such as each Saturday, Sunday. For listing the weekends, the &#x60;/api/v3/days&#x60; endpoint should be used.  All days from current year are returned by default.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions.  Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + date: the inclusive date interval to scope days to look up. When   unspecified, default is from the beginning to the end of current year.    Example: &#x60;{ \&quot;date\&quot;: { \&quot;operator\&quot;: \&quot;&lt;&gt;d\&quot;, \&quot;values\&quot;: [\&quot;2022-05-02\&quot;,\&quot;2022-05-26\&quot;] } }&#x60;   would return days between May 5 and May 26 2022, inclusive.
     * @return NonWorkingDayCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public NonWorkingDayCollectionModel listNonWorkingDays(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<NonWorkingDayCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listNonWorkingDaysRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * Lists all non working days
     * Lists all one-time non working days, such as holidays. It does not lists the non working weekdays, such as each Saturday, Sunday. For listing the weekends, the &#x60;/api/v3/days&#x60; endpoint should be used.  All days from current year are returned by default.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions.  Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + date: the inclusive date interval to scope days to look up. When   unspecified, default is from the beginning to the end of current year.    Example: &#x60;{ \&quot;date\&quot;: { \&quot;operator\&quot;: \&quot;&lt;&gt;d\&quot;, \&quot;values\&quot;: [\&quot;2022-05-02\&quot;,\&quot;2022-05-26\&quot;] } }&#x60;   would return days between May 5 and May 26 2022, inclusive.
     * @return ResponseEntity&lt;NonWorkingDayCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<NonWorkingDayCollectionModel> listNonWorkingDaysWithHttpInfo(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<NonWorkingDayCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listNonWorkingDaysRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * Lists all non working days
     * Lists all one-time non working days, such as holidays. It does not lists the non working weekdays, such as each Saturday, Sunday. For listing the weekends, the &#x60;/api/v3/days&#x60; endpoint should be used.  All days from current year are returned by default.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * @param filters JSON specifying filter conditions.  Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  + date: the inclusive date interval to scope days to look up. When   unspecified, default is from the beginning to the end of current year.    Example: &#x60;{ \&quot;date\&quot;: { \&quot;operator\&quot;: \&quot;&lt;&gt;d\&quot;, \&quot;values\&quot;: [\&quot;2022-05-02\&quot;,\&quot;2022-05-26\&quot;] } }&#x60;   would return days between May 5 and May 26 2022, inclusive.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listNonWorkingDaysWithResponseSpec(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listNonWorkingDaysRequestCreation(filters);
    }

    /**
     * Lists week days
     * Lists week days with work schedule information.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * @return WeekDayCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWeekDaysRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<WeekDayCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/days/week", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Lists week days
     * Lists week days with work schedule information.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * @return WeekDayCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public WeekDayCollectionModel listWeekDays() throws RestClientResponseException {
        ParameterizedTypeReference<WeekDayCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWeekDaysRequestCreation().body(localVarReturnType);
    }

    /**
     * Lists week days
     * Lists week days with work schedule information.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * @return ResponseEntity&lt;WeekDayCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WeekDayCollectionModel> listWeekDaysWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<WeekDayCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWeekDaysRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Lists week days
     * Lists week days with work schedule information.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWeekDaysWithResponseSpec() throws RestClientResponseException {
        return listWeekDaysRequestCreation();
    }
}
